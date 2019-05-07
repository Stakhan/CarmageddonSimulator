package mobile;


import enumeration.ObstacleType;
import enumeration.OrientedDirection;

import java.util.ArrayList;
import java.util.List;

import enumeration.Color;
import enumeration.MobileType;
import enumeration.Orientation;
import enumeration.Profil;
import immobile.structures.Lane;
import immobile.structures.Road;
import model.Cell;
import model.SimulationState;


public class Car extends MobileObject {
	
	private MovingParts movingParts;
	private Profil profil;
	private double velocity;
	private String model;
	private double maxVelocity;
	private double maxBrake;
	private Lane lane;
	private List<Integer[]> viewSpan;
	

	
	/**UP-TO-DATE
	 * 		
	 * Constructor
	 * 
	 * @param model
	 * @param length
	 * @param height
	 * @param profil
	 * @param velocity
	 * @param maxVelocity
	 * @param maxBrake
	 * @param lane
	 * @param structureParts
	 */
	public Car(MovingParts movingParts, String model, int length, int height, Profil profil,
			double velocity, double maxVelocity, double maxBrake, Lane lane) {
	
		super(length, height, initializeCarPosition(movingParts.getSimulation().getStructureParts().getStructGrid(), lane, length, height));
		
		this.movingParts = movingParts;
		
		this.profil = profil;
		this.velocity = velocity;
		this.model = model;
		this.maxVelocity = maxVelocity;
		this.maxBrake = maxBrake;
		
		this.length = length;
		this.height = height;

		this.lane = lane;
		
		this.crossingDuration = 0;
		this.waitingTime = 0;
		
		this.vision = new Vision(4*5, this);
		this.vision.update();
		
		computeCoverage(movingParts);
	}
	
	
	/**
	 * 
	 * Set the position of the car at creation depending on which lane it is linked to
	 * 
	 * @param structGrid
	 * @param lane
	 * @param length
	 * @param height
	 * @return
	 */
	static public int[] initializeCarPosition(Cell[][] structGrid, Lane lane, int length, int height) {
		
		Cell[][] grid = structGrid;

		Road road = lane.getRoad();
		OrientedDirection carDirection = lane.getOrientedDirection();
		
		// Index of car
		int roadPosition = road.getPosition();
		int carPositionOnRoad = road.getSideWalkSize() + road.getLaneSize()*(road.getIndexOfLane(lane)+1) - ((int) road.getLaneSize()/2);

		
		Integer x = Integer.valueOf(-1);
		Integer y = Integer.valueOf(-1);
		
		switch (carDirection) {
        case WE:
        	y = roadPosition + carPositionOnRoad;
        	x = (int) length/2 + length%2;
        	break;
        case NS:
			roadPosition -= 1;
			carPositionOnRoad -= 1;
			x = grid[0].length - roadPosition - carPositionOnRoad;
			y = (int) length/2 + length%2; //taking length of car in account
			break;
        case SN:
			roadPosition -= 1;
			carPositionOnRoad -=1;
			x = grid[0].length - roadPosition - carPositionOnRoad;
			y = grid.length - (((int) length/2));
			break;
        case EW:
			y = roadPosition + carPositionOnRoad;
			x = grid[0].length - ((int) length/2);
			break;
		}
		System.out.println("initializeCarPosition:"+x+","+y+" et "+grid[x][y].getX()+","+grid[x][y].getY());
		int[] position = {x,y};
		return position;

	}
	
	/**
	 * Compute the cells occupied by the current car and list them
	 * @param reference grid
	 */
	private void computeCoverage(MovingParts movingParts) {
		
		this.objectCoverage.clear(); //Clear old coverage
		
		if (visible) {
			Orientation carOrientation = this.lane.getRoad().getOrientation();
			
			int sideSpan = (int) this.height/2 + this.height%2;
			int span = (int) this.length/2 + this.length%2;

			if (carOrientation == Orientation.Horizontal) {
				int xOrigin = this.position[0] - span;
				int yOrigin = this.position[1] - sideSpan;
				
				for(int i=xOrigin; i<xOrigin+this.length; i++) {
					for(int j=yOrigin; j<yOrigin+this.height; j++) {
						Integer[] couple = {j,i};
						this.objectCoverage.add(couple);
					}
				}
			}
			else if (carOrientation == Orientation.Vertical) {
				int xOrigin = this.position[0] - sideSpan;
				int yOrigin = this.position[1] - span;
				
				for(int i=xOrigin; i<xOrigin+this.height; i++) {
					for(int j=yOrigin; j<yOrigin+this.length; j++) {
						Integer[] couple = {j,i};
						this.objectCoverage.add(couple);
					}
				}
			}
		}
		
	}
	
	
	
	// Methods related to the movement of the car
	
	/**
	 * Compilation of all actions of a car in one step. Calls other methods of this class.
	 */
	public void nextStep() {
		this.computeCoverage(this.movingParts);
		this.changeVelocity(true);
		this.vision.update();
		this.go();
		//System.out.println("Car "+this+" looking :"+this.look(10*this.length));
	}
	
	/**
	 * Changes the velocity of the car depending on the profil of its driver and whether there shall be acceleration or deceleration
	 */
	public void changeVelocity(boolean accelerate) {
		
		int sign = 1; //Positve or negative (accelerate or decelerate)
		if (!accelerate) {
			sign = -1;
		}
		
		switch (this.profil) {
		case slow:
			this.velocity += sign*3;
			break;
		case respectful:
			this.velocity += sign*5;
    		break;
		case crazy:
			this.velocity += sign*7;
			break;
		case veryCrazy:
			this.velocity += sign*10;
			break;
		}
		if (this.velocity > maxVelocity) {
			this.velocity = maxVelocity;
		}
		else if (this.velocity < 0) {
			this.velocity = 0;
		}
	}
	
	/**
	 * changes the position of the car
	 */
	public void go() {
		
		
		// the car goes to velocity*0,8 cells per state
		int distance = (int) ((int) this.velocity*3.6/0.8);
		
		
		// Changing the position of the car
		OrientedDirection carDirection = this.lane.getOrientedDirection();
		
		if (!this.inGarage()) { //Test if car is in garage position
			switch (carDirection) {
			case NS: 
				if (position[1] + distance <= this.lane.getRoad().getLength()) { //test if car is still inside of the simulation after movement
					position[1] = position[1] + distance; 
					this.visible = true;
				}
				else { //In case it leaves the simulation
					this.park();
				}
				
				break;
			case SN:
				if (position[1] - distance >= 0) {
				position[1] = position[1] - distance;
				this.visible = true;
				}
				else { //In case it leaves the simulation
					this.park();
				}
	    		break;
			case EW:
				if (position[0] - distance >= 0) {
				position[0] = position[0] - distance;
				this.visible = true;
				}
				else { //In case it leaves the simulation
					this.park();
				}
	    		break;
			case WE:
				if (position[0] + distance <= this.lane.getRoad().getLength()) {
				position[0] = position[0] + distance;
				this.visible = true;
				}
				else { //In case it leaves the simulation
					this.park();
				}
	    		break;
			}
		}
		
		
	}

	

	@Override
	public MobileType getType() {
		return MobileType.Car;
	}
	
	

	
	
	/**
	 * This function returns true if there is an obstacle in the view span of the car.
	 * The view span is 3*velocity*3.6/0.8
	 * @param gridSimulation
	 * @return
	 */
	public boolean obstacle() {
		
		// DEV-NOTE: We could add different cases (if the driver is slow, he may see less far, ...) 
		
		Orientation orientation = lane.getRoad().getOrientation();
		
		Cell[][] grid = null;
		
		if (this.movingParts.getSimulation().getListStates().size() > 1) { //We need two states to get a previous state
			//Fetching previous state
			SimulationState previousState = this.movingParts.getSimulation().getState(this.movingParts.getSimulation().getLastState().getStep()-1);
			//Fetching grid of previous step
			grid = previousState.getGrid();
		}
		else { //In case it is the first state
			grid = this.movingParts.getSimulation().getLastState().getGrid();
		}
		
		/* The driver can see the three time his speed is.
		   The faster he drives, the farther he anticipates.
		   Compute distances : he can see from his position to 3*speed/1second */
		if (orientation == Orientation.Horizontal) {
			int i = position[1] + ((int) length/2) + 1;
			for (int j = 0; j < 3*this.velocity; j++) {
				if (grid[i][j].getContainedMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return true;
				}
			}
		}
		if (orientation == Orientation.Vertical) {
			int j = position[0] + ((int) length/2) + 1;
			for (int i = 0; i < 3*this.velocity; i++) {
				if (grid[i][j].getContainedMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * UNFINISHED (ask Arthur)
	 * This function computes if there is a light traffic on the view span of the car.
	 * If there is one, it returns the color of the light
	 * If not, it returns green (which means the car can accelerate until it sees a traffic light)
	 * @return
	 */
	public Color lightTraffic(Cell[][] grid) {
		Orientation orientation = lane.getRoad().getOrientation();
		if (orientation == Orientation.Horizontal) {
			int i = position[1] + ((int) length/2) + 1;
			for (int j = 0; j < 3*this.velocity; j++) {
				if (grid[i][j].getContainedMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return Color.Green;
				}
			}
		}
		return Color.Green;
	}

	
	
	
	//Getters
	
	
	
	public Lane getLane() {
		return lane;
	}
	
	public MovingParts getMovingParts() {
		return movingParts;
	}
	
}
