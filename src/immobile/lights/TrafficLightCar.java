package immobile.lights;

import enumeration.Color;
import immobile.structures.Road;

/**
* Concrete Traffic Light class dedicated to the Cars
*/
public class TrafficLightCar extends TrafficLight{
	/**
	* Yellow light duration
	*/
	private int timeYellow;

	/**
	* Constructor
	*/
	public TrafficLightCar(Road road, Color currentColor, int timeGreen, int timeRed, int timeYellow) {
		super(road, currentColor, timeGreen, timeRed);
		this.timeYellow = timeYellow;
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

	/**
	* This method determine the change in Traffic Light Color.
	*/
	@Override
	public void changeColor() {
		switch (currentColor) {
		case Green: currentColor = Color.Yellow;
		case Yellow: currentColor = Color.Red;
		case Red: currentColor = Color.Green;
		}
	}

}
