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
	private int x;
	private int y;
	
	/*
	 * Constructors
	 */
	public Cell(int x, int y) {
		containedStructures = new ArrayList<Structure>();
		containedMobileObjects = new ArrayList<MobileObject>();
		containedRoads = new ArrayList<>();
		this.x = x;
		this.y = y;
	}

//	public Cell(Road road) {
//		containedStructures = new ArrayList<Structure>();
//		containedMobileObjects = new ArrayList<MobileObject>();
//		containedRoads = new ArrayList<Road>();
//	}

	

	
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
	public List<Road> getContainedRoads() {
		return containedRoads;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public List<Road> getcontainedRoads() {
		return containedRoads;
	}
	public Structure getContainedStructures(int index) {
		return containedStructures.get(index);
	}
	
	
	@Override
	public String toString() {
		String c = " ";
		if (containedRoads.size() > 0) {
			c = "=";
		}
		if (containedMobileObjects.size() > 0) {
			c = "o";
		}
		return c;
	}
}
