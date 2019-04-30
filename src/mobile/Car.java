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
import model.SimulationState;


public class Car extends MobileObject {
	private Profil profil;
	private MobileObject mobileObject;
	private double velocity;
	private String model;
	private double maxVelocity;
	private double maxBrake;
	private Lane lane;
	
	
	
	/**DEPRECATED
	 * Old constructor 
	 * @param velocity, represents the velocity of the car.
	 * @param model, is the type of the vehicle.
	 * @param maxAcceleration
	 * @param maxBrake
	 * @param crossingDuration
	 * @param waitingTime
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
	 * Constructor
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
	public Car(String model, int length, int height, Profil profil,
			double velocity, double maxVelocity, double maxBrake, Lane lane, StructureParts structureParts) {

		super(length, height, initializeCarPosition(structureParts, lane, length, height));
		
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
		
		computeCoverage(structureParts.getStructGrid());
	}
	
	
	static private Cell initializeCarPosition(StructureParts structureParts, Lane lane, int length, int height) {
		Cell[][] grid = structureParts.getStructGrid();
		Road road = lane.getRoad();
		OrientedDirection carDirection = lane.getOrientedDirection();
		
		// Index of car
		int roadPosition = road.getPosition();
		int carPositionOnRoad = road.getSideWalkSize() + road.getLaneSize()*(road.getIndexOfLane(lane)+1) - ((int) road.getLaneSize()/2);
		
		if (carDirection == OrientedDirection.NS) {
			int x = grid[0].length - roadPosition - carPositionOnRoad;
			int y = (int) length/2 + length%2; //taking length of car in account
			return grid[x][y];		
		}
		if (carDirection == OrientedDirection.WE) {
			int y = roadPosition + carPositionOnRoad;
			int x = (int) length/2 + length%2;
			return grid[x][y];			
		}
		
		carPositionOnRoad = road.getSideWalkSize() + road.getLaneSize()*(road.getIndexOfLane(lane)+1) - ((int) road.getLaneSize()/2);
		if (carDirection == OrientedDirection.SN) {
			int x = grid[0].length - roadPosition - carPositionOnRoad;
			int y = grid.length - ((int) length/2 + length%2);
			return grid[x][y];			
		}
		if (carDirection == OrientedDirection.EW) {
			int y = grid.length - roadPosition - carPositionOnRoad;
			int x = grid[0].length - ((int) length/2 + length%2);
			return grid[x][y];			
		}
		return null;
	}
	
	/**
	 * compute the cells occupied by the current Car and list them
	 * @param reference grid
	 */

	private void computeCoverage(Cell[][] grid) {
		
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
	 * This methods is used to start a vehicle. Its velocity goes from 0 to 10km/h.
	 */
	public void go(SimulationState grid, double maxVelocity) {
		// Changing the velocity from .. to :
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
		if (this.velocity > maxVelocity) {
			this.velocity = maxVelocity;
		}
		
		// the car goes to velocity*0,8 cells per state
		int distance = (int) ((int) this.velocity*3.6/0.8);
		
		// Changing the position of the car
		
		// /!\ The case where the car is out of the grid is not implemented
		
		OrientedDirection carDirection = this.lane.getOrientedDirection();
		
		if (carDirection == OrientedDirection.NS) {
			position.setX(position.getX() + distance);
		}
		if (carDirection == OrientedDirection.SN) {
			position.setX(position.getX() - distance);
		}
		if (carDirection == OrientedDirection.EW) {
			position.setX(position.getY() - distance);
		}
		if (carDirection == OrientedDirection.WE) {
			position.setY(position.getX() + distance);
		}
	}
	
	
	
	
	
	/**
	 *  This function computes the deceleration of a car
	 * @param minVelocity
	 * @param lane
	 */
	public void decelerate(double minVelocity, Lane lane) {
		// Changing the velocity from .. to :
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
		
		// The car can't reach a velocity under minVelocity (0, 20, 30 km/h... it depends)
		if (this.velocity < minVelocity) {
			this.velocity = minVelocity;		
			}
		
		
		// the car goes to velocity*0,8 cells per state
		int distance = (int) ((int) this.velocity*3.6/0.8);
		
		// Changing the position of the car
		
		// /!\ The case where the car is out of the grid is not implemented
		
		OrientedDirection carDirection = this.lane.getOrientedDirection();
		
		if (carDirection == OrientedDirection.NS) {
			position.setX(position.getX() + distance);
		}
		if (carDirection == OrientedDirection.SN) {
			position.setX(position.getX() - distance);
		}
		if (carDirection == OrientedDirection.EW) {
			position.setX(position.getY() - distance);
		}
		if (carDirection == OrientedDirection.WE) {
			position.setY(position.getX() + distance);
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
		// The faster he drives, the better he sees.
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
	

	
	



}
