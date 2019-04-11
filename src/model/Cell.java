package model;

import java.util.ArrayList;
import java.util.List;

import immobile.lights.TrafficLight;
import immobile.structures.Structure;
import mobile.MobileObject;

public class Cell {
	private List<Structure> containedStructures;
	private List<MobileObject> containedMobileObjects;
	private TrafficLight trafficLight;
	
	/*
	 * Constructor
	 */
	public Cell() {
		containedStructures = new ArrayList<Structure>();
		containedMobileObjects = new ArrayList<MobileObject>();		
	}
	
	/*
	 * Setters
	 */
	public void addStructure(Structure structure) {
		containedStructures.add(structure);
	}
	public void addMobileObjects(MobileObject object) {
		containedMobileObjects.add(object);
	}
	
}
