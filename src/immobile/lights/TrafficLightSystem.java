package immobile.lights;

import java.util.ArrayList;
import java.util.List;

import enumeration.Color;
import immobile.structures.Road;

/**
* The Traffic Light System coordinates the Car and Pedestrian Traffic Lights
*/
public class TrafficLightSystem {
	/**
	* Traffic Light System is a composition of Traffic Lights
	*/
	private List<TrafficLight> listLights;
	/**
	* Time to wait for changing the Traffic Light state
	private int timing;
	*/
	/**
	 * Time to wait for changing the Traffic Light state belong to the road
	*/
	private int timingMainRoad;
	private int timingSecondRoad;
	
	/**
	* This constructor builds a list of Traffic Lights for Car and Pedestrian by road.
	*/
	public TrafficLightSystem(List<Road> listRoads, int timingMainRoad, int timingSecondRoad) {
		this.timingMainRoad = timingMainRoad;
		this.timingSecondRoad = timingSecondRoad;
		
		// 3 seconds is the official duration for the Yellow Traffic Light in French agglomeration
		int timeYellow = 3;
		int timeMainRed = timingMainRoad + timeYellow;
		int timeSecondRed = timingSecondRoad + timeYellow;

		List<TrafficLight> listLights = new ArrayList<TrafficLight>();
		listLights.add(new TrafficLightCar(listRoads.get(1), Color.Green, timingMainRoad, timeMainRed, timeYellow));
		listLights.add(new TrafficLightCar(listRoads.get(2), Color.Red, timingSecondRoad, timeSecondRed, timeYellow));
		listLights.add(new TrafficLightPedestrian(listRoads.get(1), Color.Green, timingMainRoad, timeMainRed + timeYellow));
		listLights.add(new TrafficLightPedestrian(listRoads.get(2), Color.Red, timingSecondRoad, timeSecondRed + timeYellow));

	}

	/**
	* Method to 
	*/
	public void nextStep(int step) {
		int stepModulo = step % (timingMainRoad + timingSecondRoad);
	}

}
