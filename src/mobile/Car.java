package mobile;

import java.util.List;


import enumeration.MobileType;
import enumeration.StructureType;
import enumeration.CarDirection;
import enumeration.Color;
import enumeration.Orientation;
import enumeration.Profil;
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
	
	
	// Constructor
	/**
	 * This class inherits from MobileObject, and its constructor too.
	 * @param velocity, represents the velocity of the car.
	 * @param model, is the type of the vehicle.
	 * @param maxAcceleration
	 * @param maxBrake
	 * @param crossingDuration
	 * @param waitingTime
	 */
	public Car(String model, int length, int height, Cell position, Profil profil,
			double velocity, double maxVelocity, double maxBrake, int crossingDuration, 
			int waitingTime, Lane lane) {

		super(length, height, position);

		this.profil = profil;
		this.velocity = velocity;
		this.model = model;
		this.maxVelocity = maxVelocity;
		this.maxBrake = maxBrake;
		
		this.length = length;
		this.height = height;

		this.lane = lane;
	}
	
	
	
	
	
	
	
	public Car(String model, int length, int height, Profil profil,
			double velocity, double maxVelocity, double maxBrake, int crossingDuration, 
			int waitingTime, Lane lane) {

		super(length, height);
		
		this.profil = profil;
		this.velocity = velocity;
		this.model = model;
		this.maxVelocity = maxVelocity;
		this.maxBrake = maxBrake;
		
		this.length = length;
		this.height = height;

		this.lane = lane;
	}
	
	
	public void initializeCar(SimulationState grid) {
		Road road = lane.getRoad();
		CarDirection carDirection = getDirection();
		// Index of car
		int indexRoad = road.getPosition();
		int carPositionOnRoad = road.getSideWalkSize()  + road.getLaneSize()*road.getIndexOfLane(lane) - ((int) road.getLaneSize()/2) + 1;
		
		if (carDirection == CarDirection.NS) {
			int x = - indexRoad - carPositionOnRoad;
			int y = 0;
			grid.getGridValue(y, x).addMobileObjects(this);			
		}
		if (carDirection == CarDirection.WE) {
			int y = + indexRoad + carPositionOnRoad;
			int x = 0;
			grid.getGridValue(y, x).addMobileObjects(this);			
		}
		if (carDirection == CarDirection.SN) {
			int x = + indexRoad + carPositionOnRoad;
			int y = -1;
			grid.getGridValue(y, x).addMobileObjects(this);			
		}
		if (carDirection == CarDirection.EW) {
			int y = - indexRoad - carPositionOnRoad;
			int x = -1;
			grid.getGridValue(y, x).addMobileObjects(this);			
		}
	}
	
	
	
	
	
	
	// Methods
	/**
	 * This methods is used to start a vehicle. Its velocity goes from 0 to 10km/h.
	 */
	public CarDirection getDirection() {
		// Compute the direction of the car
		// Get the direction of the road where the car is
		Road road = lane.getRoad();
		Orientation orientation = road.getOrientation();
		
		// Compute simple direction 
		// Reminder : orientation = Vertical or Horizontal
		//			: direction = true or false
		if (lane.getDirection() == true) {
			if (orientation == Orientation.Horizontal) {
				return CarDirection.WE;			
			}
		}
		if (lane.getDirection() == true) {
			if (orientation == Orientation.Vertical) {
				return CarDirection.NS;			
			}
		}
		if (lane.getDirection() == false) {
			if (orientation == Orientation.Horizontal) {
				return CarDirection.EW;			
			}
		}
		if (lane.getDirection() == false) {
			if (orientation == Orientation.Vertical) {
				return CarDirection.SN;			
			}
		}
	return null; // because Java needs a return :(
	}
	
	
	

	
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
		
		CarDirection carDirection = this.getDirection();
		
		if (carDirection == CarDirection.NS) {
			position.setX(position.getX() + distance);
		}
		if (carDirection == CarDirection.SN) {
			position.setX(position.getX() - distance);
		}
		if (carDirection == CarDirection.EW) {
			position.setX(position.getY() - distance);
		}
		if (carDirection == CarDirection.WE) {
			position.setY(position.getX() + distance);
		}
	}
	
	
	
	
	
	// This function compute the deceleration of a car
	public void decelerate(SimulationState grid, double minVelocity) {
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
		
		CarDirection carDirection = this.getDirection();
		
		if (carDirection == CarDirection.NS) {
			position.setX(position.getX() + distance);
		}
		if (carDirection == CarDirection.SN) {
			position.setX(position.getX() - distance);
		}
		if (carDirection == CarDirection.EW) {
			position.setX(position.getY() - distance);
		}
		if (carDirection == CarDirection.WE) {
			position.setY(position.getX() + distance);
		}
	}
	
	

	
	// This function return a boolean which is if there is an obstacle in the vision line of the car.
	// The vision line is 3*velocity*3.6/0.8
	// It is possible to add different cases (if the driver is slow, he may sees less far...)
	/**
	 * This function return a boolean which is if there is an obstacle in the vision line of the car.
	 * @param gridSimulation
	 * @return
	 */
	public boolean obstacle(SimulationState gridSimulation) {
		Orientation orientation = lane.getRoad().getOrientation();
		// The driver can see the triple his speed is.
		// The faster he drives, the better he sees.
		// Compute distances : he can see from his position to 3*speed/1second
		if (orientation == Orientation.Horizontal) {
			int i = position.getY() + ((int) length/2) + 1;
			for (int j = 0; j < 3*this.velocity; j++) {
				if (gridSimulation.getGridValue(i, j).getListMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return true;
				}
			}
		}
		if (orientation == Orientation.Vertical) {
			int j = position.getX() + ((int) length/2) + 1;
			for (int i = 0; i < 3*this.velocity; i++) {
				if (gridSimulation.getGridValue(i, j).getListMobileObjects().size() != 0) {
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
	 * This function compute is there is a light traffic on the vision sector of the car.
	 * If there is one, it returns the color of the light
	 * If not, it returns green (which means the car can accelerate until it sees a traffic light)
	 * @return
	 */
	public Color lightTraffic(SimulationState gridSimulation) {
		Orientation orientation = lane.getRoad().getOrientation();
		if (orientation == Orientation.Horizontal) {
			int i = position.getY() + ((int) length/2) + 1;
			for (int j = 0; j < 3*this.velocity; j++) {
				if (gridSimulation.getGridValue(i, j).getListMobileObjects().size() != 0) {
					// There is a mobileObject in front of the car
					return Color.Green;
				}
			}
		}
		return Color.Green;
	}
	

	
	



}
