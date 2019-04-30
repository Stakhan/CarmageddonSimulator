package mobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.MobileType;
import enumeration.StructureType;
import model.Cell;
import model.SimulationState;

public abstract class MobileObject {

	protected int crossingDuration; // it represents the number of 
									// steps where the object takes part in the simulation
	protected int waitingTime;
	
	protected int length;
	protected int height;
	protected Cell position;
	protected Cell center;
	protected List<Cell> objectCoverage; // list of cells from a same mobile object

	public MobileObject(int length, int height, Cell position) {
		this.length = length;
		this.height = height;
		this.position = position;
		System.out.println("MobileObject position is "+position.getX()+":"+position.getY());
		objectCoverage = new ArrayList<Cell>();
	}
	
	
	// Methods

	/**
	 * Draw MobileObject on grid using its coverage
	 * @param grid where object should by drawn
	 */
	public void draw(Cell[][] grid) {
		for(Cell cell : objectCoverage) {
			int x = cell.getX();
			int y = cell.getY();
			grid[x][y].addMobileObjects(this);
		}
	}

	/**
	 * Abstract method that gives the type of such objects
	 * @return type of this MobileObject
	 */
	public abstract MobileType getType();

}
