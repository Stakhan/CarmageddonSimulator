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
<<<<<<< HEAD
=======
	private int x;
	private int y;
>>>>>>> eb3eddc6473fbe42e32d60a116a30f1c51e2536b
	
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
<<<<<<< HEAD
	public Cell(Road road) {
		containedStructures = new ArrayList<Structure>();
		containedMobileObjects = new ArrayList<MobileObject>();
		containedRoads = new ArrayList<Road>();
	}
=======
	
>>>>>>> eb3eddc6473fbe42e32d60a116a30f1c51e2536b
	
	/**
	 * Setters
	 */
	public void addStructure(Structure structure) {
		containedStructures.add(structure);
	}
	public void addMobileObjects(MobileObject object) {
		containedMobileObjects.add(object);
	}
<<<<<<< HEAD
	public void addRoad(Road road) {
		this.containedRoads.add(road);
=======

	public void addRoad(Road road) {
		containedRoads.add(road);
>>>>>>> eb3eddc6473fbe42e32d60a116a30f1c51e2536b
	}
	
	/**
	 * Getters
	 */
<<<<<<< HEAD
	public Road getcontainedRoads(int index) {
		return containedRoads.get(index);
=======
	public List<Road> getContainedRoads() {
		return containedRoads;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
>>>>>>> eb3eddc6473fbe42e32d60a116a30f1c51e2536b
	}
	public List<Road> getcontainedRoads() {
		return containedRoads;
	}
	public Structure getContainedStructures(int index) {
		return containedStructures.get(index);
	}
	
	
	@Override
	public String toString() {
<<<<<<< HEAD
		return containedRoads.getClass().getSimpleName();
=======
		String c = " ";
		if (containedRoads.size() > 0) {
			c = "=";
		}
		if (containedMobileObjects.size() > 0) {
			c = "o";
		}
		return c;
>>>>>>> eb3eddc6473fbe42e32d60a116a30f1c51e2536b
	}
}
