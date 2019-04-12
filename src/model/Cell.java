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
	private List<Road> containedRoads;
	
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
		containedRoads = new ArrayList<Road>();
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
	public void addRoad(Road road) {
		this.containedRoads.add(road);
	}
	
	/**
	 * Getters
	 */
	public Road getcontainedRoads(int index) {
		return containedRoads.get(index);
	}
	public List<Road> getcontainedRoads() {
		return containedRoads;
	}
	public Structure getContainedStructures(int index) {
		return containedStructures.get(index);
	}
	
	
	@Override
	public String toString() {
		return containedRoads.getClass().getSimpleName();
	}
}
