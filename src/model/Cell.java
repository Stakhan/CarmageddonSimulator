package model;

import java.util.ArrayList;
import java.util.List;

import immobile.lights.TrafficLight;
import immobile.structures.Road;
import immobile.structures.Structure;
import mobile.MobileObject;

public class Cell {
	private List<Structure> containedStructures;
	private List<MobileObject> containedMobileObjects;
	private TrafficLight trafficLight;
	private Road containedRoad;
	
	/*
	 * Constructors
	 */
	public Cell() {
		containedStructures = new ArrayList<Structure>();
		containedMobileObjects = new ArrayList<MobileObject>();
	}
	public Cell(Road road) {
		containedStructures = new ArrayList<Structure>();
		containedMobileObjects = new ArrayList<MobileObject>();
		containedRoad = road;
		System.out.println(containedRoad.getClass().getSimpleName());
	}
	
	/**
	 * Setters
	 */
	public void addStructure(Structure structure) {
		containedStructures.add(structure);
	}
	public void addMobileObjects(MobileObject object) {
		containedMobileObjects.add(object);
	}
	public void setRoad(Road road) {
		this.containedRoad = road;
	}
	
	/**
	 * Getters
	 */
	public Road getContainedRoad() {
		return containedRoad;
	}
	
	@Override
	public String toString() {
		return containedRoad.getClass().getSimpleName();
	}
}
