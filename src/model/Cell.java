package model;

import java.util.List;

import immobile.Structure;
import immobile.TrafficLight;
import mobile.MobileObject;

public class Cell {
	private List<Structure> containedStructures;
	private List<MobileObject> containedMobileObjects;
	private TrafficLight trafficLight;
}
