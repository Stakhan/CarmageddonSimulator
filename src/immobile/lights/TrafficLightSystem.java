package immobile.lights;

import java.util.ArrayList;
import java.util.List;

import enumeration.TrafficColor;
import enumeration.Orientation;
import immobile.structures.Road;
import immobile.structures.Structure;
import mobile.MobileObject;
import model.Cell;
import immobile.StructureParts;

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
	private StructureParts structureParts;
	private int timingMainRoad;  // green horizontal
	private int timingSecondRoad;
	private final int timeYellow = 3; // 3 seconds is the official duration for the Yellow Traffic Light in French agglomeration

	/**
	* This constructor builds a determined list of Traffic Lights for Car and Pedestrian by road.
	*  @param listRoads
	 * @param timingMainRoad
	 * @param timingSecondRoad
	*/
	public TrafficLightSystem(List<Road> listRoads, int timingMainRoad, int timingSecondRoad, StructureParts structureParts) {
		this.timingMainRoad = timingMainRoad;
		this.timingSecondRoad = timingSecondRoad;
		
		List<TrafficLight> listLights = new ArrayList<TrafficLight>();
		listLights.add(new TrafficLightCar(listRoads.get(0), TrafficColor.Green));
		listLights.add(new TrafficLightCar(listRoads.get(1), TrafficColor.Red));
		listLights.add(new TrafficLightPedestrian(listRoads.get(0), TrafficColor.Green));
		listLights.add(new TrafficLightPedestrian(listRoads.get(1), TrafficColor.Red));
		
		this.listLights = listLights;
		this.structureParts = structureParts;
	}
	
	/**
	 * Constructor for deep copy (see method clone())
	 * @param listLights
	 * @param timingMainRoad
	 * @param timingSecondRoad
	 */
	public TrafficLightSystem(int timingMainRoad, int timingSecondRoad, List<TrafficLight> listLights) {
		this.listLights = listLights;
		this.timingMainRoad = timingMainRoad;
		this.timingSecondRoad = timingSecondRoad;
	}
	
	
	

	/**
	 * This method determines the color changing of the traffic lights from the step of the simulation.
	 * It applies a modulo corresponding at a traffic lights cycle and change the colors according
	 * to the result and the traffic lights timings
	 * @param step of the simulation
	 * @return 
	 * @return 
	 */
	public void nextStep(int step) {
		int stepModulo = step % (timingMainRoad + timeYellow + timingSecondRoad + timeYellow);
		int mainRoadCrossingLength = this.structureParts.getRoad(0).getRoadSize() - 2*this.structureParts.getRoad(0).getSideWalkSize();
		int secondRoadCrossingLength = this.structureParts.getRoad(1).getRoadSize() - 2*this.structureParts.getRoad(1).getSideWalkSize();
		if (stepModulo == 0) {
			// Beginning of the cycle : initial situation
			listLights.get(0).setCurrentColor(TrafficColor.Green);
			listLights.get(1).setCurrentColor(TrafficColor.Red);
			listLights.get(2).setCurrentColor(TrafficColor.Green);
		}
		else if (stepModulo == (timingMainRoad + timeYellow - mainRoadCrossingLength)) {
			listLights.get(2).setCurrentColor(TrafficColor.Red);
		}
		else if (stepModulo == timingMainRoad) {
			// End of the main road green light
			listLights.get(0).setCurrentColor(TrafficColor.Yellow);
		}
		else if (stepModulo == (timingMainRoad + timeYellow)) {
			// Beginning of the second road green light
			listLights.get(0).setCurrentColor(TrafficColor.Red);
			listLights.get(1).setCurrentColor(TrafficColor.Green);
			listLights.get(3).setCurrentColor(TrafficColor.Green);
		}
		else if (stepModulo == (timingMainRoad + 2 * timeYellow + timingSecondRoad) - secondRoadCrossingLength) {
			listLights.get(3).setCurrentColor(TrafficColor.Red);
			//System.out.println("feu pieton vertical");
		}
		else if (stepModulo == (timingMainRoad + timeYellow + timingSecondRoad)) {
			// End of the second road green light
			listLights.get(1).setCurrentColor(TrafficColor.Yellow);
		}
	}

	@Override
	public String toString() {
		return "TrafficLightSystem : " + "\nMain road car traffic light is " + listLights.get(0).currentColor 
				+ "\nSecond road car traffic light is " + listLights.get(1).currentColor 
				+ "\nMain road pedestrian traffic light is " + listLights.get(2).currentColor 
				+ "\nSecond road pedestrian traffic light is " + listLights.get(3).currentColor ;
	}
	
	/*
	 * Getters
	 */
	public int getTimingMainRoad() {
		return timingMainRoad;
	}

	public void setTimingMainRoad(int timingMainRoad) {
		this.timingMainRoad = timingMainRoad;
	}

	public int getTimingSecondRoad() {
		return timingSecondRoad;
	}

	public void setTimingSecondRoad(int timingSecondRoad) {
		this.timingSecondRoad = timingSecondRoad;
	}
	
	public List<TrafficLight> getListLights() {
		return listLights;
	}

	public List<TrafficLight> getListLightsDeepCopy() {
		
		List<TrafficLight> newList = new ArrayList<TrafficLight>();
		
		for(TrafficLight light : this.listLights) {
		    newList.add(light.clone());
		}
		return newList;
	}
	
	@Override
	public TrafficLightSystem clone() {
		
        return new TrafficLightSystem(this.timingMainRoad, this.timingSecondRoad, getListLightsDeepCopy()/*(List<TrafficLight>) ((ArrayList<TrafficLight>) this.getListLights()).clone()*/);
    
	}
}
	





//	public static void main(String args[]){
//		List<Road> listRoads = new ArrayList<Road>();
//		listRoads.add(new Road(10, 2, 0, Orientation.Horizontal, 1, false));
//		listRoads.add(new Road(10, 2, 0, Orientation.Vertical, 1, false));
//		TrafficLightSystem trafficLightSystem = new TrafficLightSystem(listRoads, 15, 12);
//		System.out.println("\nparam : timingMainRoad=" + timingMainRoad + ", timingSecondRoad=" + timingSecondRoad);
//		for(int i = 0; i <= 69; i++) {
//			trafficLightSystem.nextStep(i);
//			if (i % 3 == 0) {
//				System.out.println("\nStep " + i + ", stepmodulo " + i % (timingMainRoad + timeYellow + timingSecondRoad + timeYellow));
//				System.out.println(trafficLightSystem);
//			}
//		}
//	}

