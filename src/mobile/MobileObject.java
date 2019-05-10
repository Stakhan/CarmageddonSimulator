package mobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.MobileType;
import model.Cell;

public abstract class MobileObject {

	protected int crossingDuration; // it represents the number of 
									// steps where the object takes part in the simulation
	protected int waitingTime;
	
	protected int length;
	protected int height;
	
	protected int[] position;
	protected List<Integer[]> objectCoverage; // list of cells from a same mobile object
	
	protected boolean visible;
	
	public MobileObject(int length, int height, int[] position) {
		this.length = length;
		this.height = height;
		
		this.position = position;
		objectCoverage = new ArrayList<Integer[]>();
		
		this.visible = true;
		
	}
	
	
	// Methods

	/**
	 * Draw MobileObject on grid using its coverage
	 * @param grid where object should be drawn
	 */
	public void draw(Cell[][] grid) {
		if (visible) {
			for(Integer[] cellCoord : objectCoverage) {
				int x = cellCoord[0];
				int y = cellCoord[1];
				grid[x][y].addMobileObjects(this);
			}
		}
	}

	
	/**
	 * Test if car is in garage position
	 * @return boolean
	 */
	public boolean inGarage() {
		if (this.position[0] == -1 && this.position[1] == -1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Put car in position (-1,-1) and set it as invisible
	 */
	public void park() {
		position[0] = -1; //Putting car into garage position
		position[1] = -1;
		this.visible = false; //Set as invisible
	}
	
	
	/**
	 * Abstract method that gives the type of such objects
	 * @return type of this MobileObject
	 */
	public abstract MobileType getType();

	//Getters
	
	public int[] getPosition() {
		return position;
	}
	
	public List<Integer[]> getObjectCoverage() {
		return objectCoverage;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getCrossingDuration() {
		return crossingDuration;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
}
