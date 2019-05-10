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
	private int velocity;
	private String model;
	private int maxVelocity;
	private double maxBrake;
	private Lane lane;
	private Vision vision;
	

	
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
			int velocity, int maxVelocity, double maxBrake, Lane lane) {
	
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
		
		// param 5*4 a changer
		this.vision = new Vision(5 * 4, this);
		
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
		int[] position = {x, y};
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
						
						// test if the coverage is in the simulation
						int laneLength = lane.getRoad().getLength();
						if (i < laneLength) {
							Integer[] couple = {j,i};
							this.objectCoverage.add(couple);
						}
					}
				}
			}
			
			else if (carOrientation == Orientation.Vertical) {
				int xOrigin = this.position[0] - sideSpan;
				int yOrigin = this.position[1] - span;
				
				for(int i=xOrigin; i<xOrigin+this.height; i++) {
					for(int j=yOrigin; j<yOrigin+this.length; j++) {
						
						// test if the coverage is in the simulation
						int laneLength = lane.getRoad().getLength();
						if (j < laneLength) {
							Integer[] couple = {j,i};
							this.objectCoverage.add(couple);
						}
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
		this.go();
		this.computeCoverage(this.movingParts);
		this.changeVelocity(true);
		this.vision.setViewSpanDepth(this.velocity * 3); // To have a deeper vision : *3
		this.vision.updateView(this.velocity * 3);
		this.vision.look();
		System.out.println("CROSSING SECTION : " + isAtCrossingSection());
		System.out.println("viewSpanDepth : " + this.vision.getViewSpanDepth());
		//List<Integer[]> test = this.vision.getViewList();
		//System.out.println("viewSpanList : " + this.vision.getViewList());
		//this.vision.toString();
		
		System.out.println("car position : " + position[0] + "," + position[1]);
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
		int distance = (int) ((int) this.velocity/1);
		
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

	
	
	
	public boolean isAtCrossingSection() {

		if(!inGarage()) {
			Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid(); // get the grid of the simulation, to know the position of the different sidewalk
			if (grid[position[1]][position[0]].getContainedLights().size() != 0) {
				return true;
			}
			return false;
		}
		return false;
				
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public MobileType getType() {
		return MobileType.Car;
	}
	
	
	
	
	//Getters
	
	
	
	public Lane getLane() {
		return lane;
	}
	
	public MovingParts getMovingParts() {
		return movingParts;
	}
	
	public Vision getVision() {
		return vision;
	}
	
	public double getWaitingTime() {
		return waitingTime;
	}
	
	public double getCrossingDuration() {
		return crossingDuration;
	}
	

}
