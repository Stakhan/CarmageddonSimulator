package mobile;

import java.util.ArrayList;
import java.util.List;

public abstract class MovingParts {
	
	private List<Car> listCars;
	private List<Pedestrian> listPedestrians;
	
	/**
	 * Constructor
	 */
	public MovingParts() {
		listCars = new ArrayList<Car>();
		
	}
	
	/**
	 * Add MovingObjects to the simulation
	 */
	public void populate(int numberToAdd) {
		for(int i=0; i<numberToAdd; i++) {
			//listCars.add(new Car(velocity, model, maxAcceleration, maxBrake, crossingDuration, waitingTime))
		}
	}
}
