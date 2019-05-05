package mobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.MobileType;
import jdk.internal.util.xml.impl.Pair;
import model.Cell;

public abstract class MobileObject {

	protected int crossingDuration; // it represents the number of 
									// steps where the object takes part in the simulation
	protected int waitingTime;
	
	protected int length;
	protected int height;
	protected int[] position;
	protected boolean visible;
	protected List<Integer[]> objectCoverage; // list of cells from a same mobile object

	public MobileObject(int length, int height, int[] position) {
		this.length = length;
		this.height = height;
		this.position = position;
		this.visible = true;
		objectCoverage = new ArrayList<Integer[]>();
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
	 * Abstract method that gives the type of such objects
	 * @return type of this MobileObject
	 */
	public abstract MobileType getType();

	//Getters
	public int[] getPosition() {
		return position;
	}
}
