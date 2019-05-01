package mobile;


import enumeration.MobileType;
import enumeration.OrientedDirection;
import enumeration.Color;
import enumeration.Orientation;
import enumeration.Profil;
import immobile.StructureParts;
import immobile.structures.Lane;
import immobile.structures.Road;
import model.Cell;
import model.ConfigureStructure;
import model.SimulationState;


public class Car extends MobileObject {
	private MovingParts movingParts;
	private Profil profil;
	private MobileObject mobileObject;
	private double velocity;
	private String model;
	private double maxVelocity;
	private double maxBrake;
	private Lane lane;
	
	/**DEPRECATED
	 * Old constructor 
	 * @param model, is the type of the vehicle.
	 * @param length
	 * @param heigth
	 * @param position
	 * @param profil
	 * @param velocity, represents the velocity of the car.
	 * @param maxVelocity
	 * @param maxBrake
	 * @param lane
	 */
//	public Car(String model, int length, int height, Cell position, Profil profil,
//			double velocity, double maxVelocity, double maxBrake, Lane lane) {
//
//		super(length, height, position);
//
//		this.profil = profil;
//		this.velocity = velocity;
//		this.model = model;
//		this.maxVelocity = maxVelocity;
//		this.maxBrake = maxBrake;
//		
//		this.length = length;
//		this.height = height;
//
//		this.lane = lane;
//		
//		this.crossingDuration = 0;
//		this.waitingTime = 0;
//	}
	
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
		
		computeCoverage(movingParts);
	}
	
	

	static private Cell initializeCarPosition(Cell[][] structGrid, Lane lane, int length, int height) {
		
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
		/**

		if (carDirection == OrientedDirection.WE) {
			y = roadPosition + carPositionOnRoad;
			x = (int) length/2 + length%2;
		}
		if (carDirection == OrientedDirection.EW) {
			int y = roadPosition + carPositionOnRoad ;
			int x = grid[0].length - ((int) length/2);
			return grid[x][y];			
		}
		roadPosition -= 1; //Adjustment to match exact position
		carPositionOnRoad -= 1; //idem
		if (carDirection == OrientedDirection.NS) {
			roadPosition -= 1;
			carPositionOnRoad -= 1;
			x = grid[0].length - roadPosition - carPositionOnRoad;
			y = (int) length/2 + length%2; //taking length of car in account
		}
		if (carDirection == OrientedDirection.SN) {
			roadPosition -= 1;
			carPositionOnRoad -=1;
			x = grid[0].length - roadPosition - carPositionOnRoad;
			y = grid.length - (((int) length/2));
		}
		if (carDirection == OrientedDirection.EW) {
			y = roadPosition + carPositionOnRoad ;
			x = grid[0].length - ((int) length/2);
		}
		*/
		return grid[x][y];

	}
	
	/**
	 * compute the cells occupied by the current Car and list them
	 * @param reference grid
	 */
	private void computeCoverage(MovingParts movingParts) {
		
		this.objectCoverage.clear(); //Clear old coverage
		
		Cell[][] grid = movingParts.getSimulation().getStructureParts().getStructGrid(); //Reference grid

		
		Orientation carOrientation = this.lane.getRoad().getOrientation();
		
		int sideSpan = (int) this.height/2 + this.height%2;
		int span = (int) this.length/2 + this.length%2;
		
		if (carOrientation == Orientation.Horizontal) {
			int xOrigin = this.position.getX() - span;
			int yOrigin = this.position.getY() - sideSpan;
			
			for(int i=xOrigin; i<xOrigin+this.length; i++) {
				for(int j=yOrigin; j<yOrigin+this.height; j++) {
					this.objectCoverage.add(grid[j][i]);
				}
			}
		}
		else if (carOrientation == Orientation.Vertical) {
			int xOrigin = this.position.getX() - sideSpan;
			int yOrigin = this.position.getY() - span;
			
			for(int i=xOrigin; i<xOrigin+this.height; i++) {
				for(int j=yOrigin; j<yOrigin+this.length; j++) {
					this.objectCoverage.add(grid[j][i]);
				}
			}
		}
	}
	
	
	// Methods
	
	/**
	 * This method changes the velocity and the position of the car
	 */
	public void go() {
		// Changing the velocity from .. to :
		switch (profil) {
		case slow: this.velocity += 3;
    	break;
		case respectful: this.velocity += 5;
    	break;
		case crazy: this.velocity += 7;
    	break;
		case veryCrazy: this.velocity += 10;
    	break;
		}
		/**
		if (profil == Profil.slow) {		// to +3km/h
			this.velocity += 3;
		}
		if (profil == Profil.respectful) {		// to +5km/h
			this.velocity += 5;
		}
		if (profil == Profil.crazy) {		// to +7km/h
			this.velocity += 7;
		}
		if (profil == Profil.veryCrazy) {		// to +10km/h
			this.velocity += 10;
		}
		if (this.velocity > this.maxVelocity) {
			this.velocity = this.maxVelocity;

		*/
		if (this.velocity > maxVelocity) {
			this.velocity = maxVelocity;

		}
		// the car goes to velocity*0,8 cells per state
		int distance = (int) ((int) this.velocity*3.6/0.8);
		
		
		// Changing the position of the car
		
		// /!\ The case where the car is out of the grid is not implemented
		
		OrientedDirection carDirection = this.lane.getOrientedDirection();
		/*
		switch (carDirection) {
		case NS: position.setX(position.getX() + distance);
    	break;
		case SN: position.setX(position.getX() - distance);
    	break;
		case EW: position.setX(position.getY() - distance);
    	break;
		case WE: position.setY(position.getX() + distance);
    	break;
		}
		*/
		if (carDirection == OrientedDirection.NS) {
			position.setY(position.getY() + distance);
		}
		if (carDirection == OrientedDirection.SN) {
			position.setY(position.getY() - distance);
		}
		if (carDirection == OrientedDirection.EW) {
			position.setX(position.getX() - distance);
		}
		if (carDirection == OrientedDirection.WE) {
			position.setX(position.getX() + distance);
		}
		
	}
	
	
	/**
	 *  This function computes the deceleration of a car
	 * @param minVelocity
	 * @param lane
	 */
	public void decelerate(double minVelocity, Lane lane) {
		// Changing the velocity from .. to :
		switch (profil) {
		case slow: this.velocity -= 3;
    	break;
		case respectful: this.velocity -= 5;
    	break;
		case crazy: this.velocity -= 7;
    	break;
		case veryCrazy: this.velocity -= 10;
    	break;
		}
		/**
		if (profil == Profil.slow) {		// to -3km/h
			this.velocity -= 3;
		}
		if (profil == Profil.respectful) {		// to -5km/h
			this.velocity -= 5;
		}
		if (profil == Profil.crazy) {		// to -7km/h
			this.velocity -= 7;
		}
		if (profil == Profil.veryCrazy) {		// to -10km/h
			this.velocity -= 10;
		}
		*/
		
		// The car can't reach a velocity under minVelocity (0, 20, 30 km/h... it depends)
		if (this.velocity < minVelocity) {
			this.velocity = minVelocity;		
			}
		
		// the car goes to velocity*0,8 cells per state
		//int distance = (int) ((int) this.velocity*3.6/0.8);
		int distance = 1;
		// Changing the position of the car
		
		// /!\ The case where the car is out of the grid is not implemented
		
		OrientedDirection carDirection = this.lane.getOrientedDirection();
		/*
		switch (carDirection) {
		case NS: position.setX(position.getX() + distance);
    	break;
		case SN: position.setX(position.getX() - distance);
    	break;
		case EW: position.setX(position.getY() - distance);
    	break;
		case WE: position.setY(position.getX() + distance);
    	break;
		}
		*/
		if (carDirection == OrientedDirection.NS) {
			position.setY(position.getY() + distance);
		}
		if (carDirection == OrientedDirection.SN) {
			position.setY(position.getY() - distance);
		}
		if (carDirection == OrientedDirection.EW) {
			position.setX(position.getX() - distance);
		}
		if (carDirection == OrientedDirection.WE) {
			position.setX(position.getX() + distance);
		}
		
	}
	
	// This function returns a boolean which is if there is an obstacle in the vision line of the car.
	// The vision line is 3*velocity*3.6/0.8
	// It is possible to add different cases (if the driver is slow, he may sees less far...)
	/**
	 * This function returns true if there is an obstacle in the view span of the car.
	 * @param gridSimulation
	 * @return
	 */
	public boolean obstacle(Cell[][] grid) {
		Orientation orientation = lane.getRoad().getOrientation();
		// The driver can see the triple his speed is.
		// The faster he drives, the farther he anticipates.
		// Compute distances : he can see from his position to 3*speed/1second
		if (orientation == Orientation.Horizontal) {
			int i = position.getY() + ((int) length/2) + 1;
			for (int j = 0; j < 3*this.velocity; j++) {
				if (grid[i][j].getListMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return true;
				}
			}
		}
		if (orientation == Orientation.Vertical) {
			int j = position.getX() + ((int) length/2) + 1;
			for (int i = 0; i < 3*this.velocity; i++) {
				if (grid[i][j].getListMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public MobileType getType() {
		return MobileType.Car;
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
			int i = position.getY() + ((int) length/2) + 1;
			for (int j = 0; j < 3*this.velocity; j++) {
				if (grid[i][j].getListMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return Color.Green;
				}
			}
		}
		return Color.Green;
	}

	public void nextStep() {
		this.computeCoverage(this.movingParts);
		this.go();
	}

}
