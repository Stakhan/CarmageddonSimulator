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

	/**
	* Constructor
	*/
	public TrafficLightCar(Road road, Color currentColor) { // deprecated: , int timeGreen, int timeRed, int timeYellow
		super(road, currentColor); // deprecated: , timeGreen, timeRed
		/*
		 * deprecated
		this.timeYellow = timeYellow;
		 */
	}

	@Override
	public void setOrientation(Road road) {
		// TODO Auto-generated method stub
		super.setOrientation(road);
	}

	@Override
	public void setCurrentColor(Color currentColor) {
		// TODO Auto-generated method stub
		super.setCurrentColor(currentColor);
	}

	/*
	 * deprecated
	@Override
	public void setTimeGreen(int timeGreen) {
		// TODO Auto-generated method stub
		super.setTimeGreen(timeGreen);
	}
	@Override
	public void setTimeRed(int timeRed) {
		// TODO Auto-generated method stub
		super.setTimeRed(timeRed);
	}
	@Override
	public void changeColor() {
		switch (currentColor) {
		case Green: currentColor = Color.Yellow;
		case Yellow: currentColor = Color.Red;
		case Red: currentColor = Color.Green;
		}
	 */
	}
}
