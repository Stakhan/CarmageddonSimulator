package immobile.lights;

import java.util.ArrayList;
import java.util.List;

import enumeration.Color;
import immobile.structures.Road;

/**
* Given the duration of the Green Light for the main and the secondary road, 
* the Traffic Light System generates the Traffic Lights for Cars and Pedestrians
* and coordinates the Color changing. 
*/
public class TrafficLightSystem {
	/**
	* Traffic Light System is a composition of Traffic Lights.
	*/
	private List<TrafficLight> listLights;
	/**
	 * Time to wait for changing the Traffic Light state belong to the road.
	*/
	private int timingMainRoad;
	private int timingSecondRoad;

	/**
	 * Duration of each traffic ligth color
	*/
	// 3 seconds is the official duration for the Yellow Traffic Light in French agglomeration
	int timeYellow = 3;
	int timeMainRed = timingSecondRoad + timeYellow;
	int timeSecondRed = timingMainRoad + timeYellow;

	/**
	* This constructor builds a determined list of Traffic Lights for Car and Pedestrian by road.
	*/
	public TrafficLightSystem(List<Road> listRoads, int timingMainRoad, int timingSecondRoad) {
		this.timingMainRoad = timingMainRoad;
		this.timingSecondRoad = timingSecondRoad;
		
		List<TrafficLight> listLights = new ArrayList<TrafficLight>();
		listLights.add(new TrafficLightCar(listRoads.get(1), Color.Green));
		listLights.add(new TrafficLightCar(listRoads.get(2), Color.Red));
		listLights.add(new TrafficLightPedestrian(listRoads.get(1), Color.Green));
		listLights.add(new TrafficLightPedestrian(listRoads.get(2), Color.Red));

		/*
		 * deprecated
		List<TrafficLight> listLights = new ArrayList<TrafficLight>();
		listLights.add(new TrafficLightCar(listRoads.get(1), Color.Green, timingMainRoad, timeMainRed, timeYellow));
		listLights.add(new TrafficLightCar(listRoads.get(2), Color.Red, timingSecondRoad, timeSecondRed, timeYellow));
		listLights.add(new TrafficLightPedestrian(listRoads.get(1), Color.Green, timingMainRoad, timeMainRed + timeYellow));
		listLights.add(new TrafficLightPedestrian(listRoads.get(2), Color.Red, timingSecondRoad, timeSecondRed + timeYellow));
		*/
}

	/**
	* This method determines the color changing of the traffic lights from the step of the simulation.
	* It applies a modulo corresponding at a traffic lights cycle and change the colors according
	* to the result and the traffic lights timings
	* @param step of the simulation
	*/
	public void nextStep(int step) {
		int stepModulo = step % (timingMainRoad + timingSecondRoad);
		if (stepModulo == 0) {
			// Beginning of the cycle / Initial situation
			this.listLights.get(0).setCurrentColor(Color.Green);
			this.listLights.get(1).setCurrentColor(Color.Red);
			this.listLights.get(2).setCurrentColor(Color.Green);
		}
		else if (stepModulo == timingMainRoad) {
			// End of the main road green light
			this.listLights.get(0).setCurrentColor(Color.Yellow);
			this.listLights.get(2).setCurrentColor(Color.Red);
		}
		else if (stepModulo == timeMainRed) {
			// Beginning of the second road green light
			this.listLights.get(0).setCurrentColor(Color.Red);
			this.listLights.get(1).setCurrentColor(Color.Green);
			this.listLights.get(3).setCurrentColor(Color.Green);
		}
		else if (stepModulo == timeMainRed + timingSecondRoad) {
			// End of the second road green light
			this.listLights.get(1).setCurrentColor(Color.Yellow);
			this.listLights.get(3).setCurrentColor(Color.Red);
		}
	}
}
