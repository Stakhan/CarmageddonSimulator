package model;

import java.util.ArrayList;
import java.util.List;

import enumeration.Direction;
import enumeration.MobileType;
import enumeration.OrientedDirection;
import enumeration.StructureType;

import java.lang.Class;

import immobile.lights.TrafficLight;
import immobile.structures.Road;
import immobile.structures.Structure;
import mobile.Car;
import mobile.MobileObject;

public class Cell implements Cloneable{
	private List<Structure> containedStructures;
	private List<MobileObject> containedMobileObjects;
	private TrafficLight trafficLight;
	private List<Road> containedRoads;
	private int x;
	private int y;
	
	/*
	 * Constructor
	 */
	public Cell(int x, int y) {
		containedStructures = new ArrayList<Structure>();
		containedMobileObjects = new ArrayList<MobileObject>();
		containedRoads = new ArrayList<>();
		this.x = x;
		this.y = y;
	}


	/*
	 * Constructor for clone() method
	 */
	public Cell(List<Structure> containedStructures, List<MobileObject> containedMobileObjects,
			TrafficLight trafficLight, List<Road> containedRoads, int x, int y) {
		this.containedStructures = containedStructures;
		this.containedMobileObjects = containedMobileObjects;
		this.trafficLight = trafficLight;
		this.containedRoads = containedRoads;
		this.x = x;
		this.y = y;
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
	
	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}
	
	public void setX(int i) {
		this.x = i;
	}

	public void setY(int i) {
		this.y = i;
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
	
	public TrafficLight getTrafficLight() {
		return trafficLight;
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
	
	public List<MobileObject> getContainedMobileObjects() {
		return containedMobileObjects;
	}
	public MobileObject getContainedMobileObjects(int index) {
		return containedMobileObjects.get(index);
	}
	
	public List<Structure> getContainedStructures() {
		return containedStructures;
	}
	public Structure getContainedStructures(int index) {
		return containedStructures.get(index);
	}
	
	public boolean contains(StructureType type) {
		for(Structure structure : containedStructures) {
			if (structure.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}
	public boolean contains(MobileType type) {
		for(MobileObject mobileObject : containedMobileObjects) {
			if (mobileObject.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}


	

	
	
	/*
	 * This method is necessary for deep copy of the grid
	 */
	@Override
	public Cell clone() {
		
        return new Cell((List<Structure>) ((ArrayList<Structure>) this.containedStructures).clone(), (List<MobileObject>) ((ArrayList) this.containedMobileObjects).clone(), this.trafficLight, (List<Road>) ((ArrayList)this.containedRoads).clone(), this.x, this.y);
    
	}
}
