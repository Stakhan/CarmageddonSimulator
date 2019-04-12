package immobile.lights;

import enumeration.Color;
import immobile.structures.Road;

public class TrafficLightPedestrian extends TrafficLight{
	public TrafficLightPedestrian(Road road, Color currentColor, int timeGreen, int timeRed) {
		super(road, currentColor, timeGreen, timeRed);
	}

	/**
	* 
	*/
	@Override
	public void changeColor() {
		// TODO Auto-generated method stub
		super.changeColor();
	}

}
