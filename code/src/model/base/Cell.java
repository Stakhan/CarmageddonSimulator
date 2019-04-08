package model.base;

import java.util.List;

import model.mobile.MobileObject;
import model.structure.Structure;
import model.structure.TrafficLight;

public class Cell {
	private List<Structure> containedStructures;
	private List<MobileObject> containedMobileObjects;
	private TrafficLight trafficLight;
}
