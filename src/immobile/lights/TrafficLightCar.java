package immobile.lights;

import enumeration.Color;
import immobile.structures.Road;

/**
* Concrete Traffic Light class dedicated to the Cars
*/
public class TrafficLightCar extends TrafficLight{
	/**
	* deprecated
	private int timeYellow;
	*/

	public TrafficLightCar(Road road, Color currentColor) { // deprecated: , int timeGreen, int timeRed, int timeYellow
		super(road, currentColor); // deprecated: , timeGreen, timeRed
		/*
		 * deprecated
		this.timeYellow = timeYellow;
		 */
	}

	/*
	 * deprecated
	@Override
	public void changeColor() {
		switch (currentColor) {
		case Green: currentColor = Color.Yellow;
		case Yellow: currentColor = Color.Red;
		case Red: currentColor = Color.Green;
		}
	}
	 */
}
