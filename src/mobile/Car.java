package mobile;

import java.util.List;

import enumeration.MobileType;
import enumeration.StructureType;

public class Car extends MobileObject {
	
	private double velocity;
	private String model;
	private double maxAcceleration;
	private double maxBrake;
	
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
	public Car(double velocity, String model, double maxAcceleration, double maxBrake, int crossingDuration, int waitingTime) {
		this.velocity = velocity;
		this.model = model;
		this.maxAcceleration = maxAcceleration;
		this.maxBrake = maxBrake;
		
		int length = 5;
		int height = 3;
		int[] position = computePosition(length, height);
		this.position = position;
		this.length = length;
		this.height = height;
	}
	
	
	
	
	// Methods
	/**
	 * This methods is used to start a vehicle. Its velocity goes from 0 to 10km/h.
	 */
	public void go() {
		this.velocity = 10;
		position[1] += 1; //TEST the car move on		
	}
	
	/**
	 * This methods is used to know if a vehicle can drive on the road.
	 * @return true if the vehicle can continue, false otherwise.
	 */
	public boolean canGo() {
		
		return true;
	}
	

	@Override
	public MobileType getType() {
		return MobileType.Car;
	}

}
