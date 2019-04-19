package mobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.MobileType;
import enumeration.StructureType;
import model.Cell;
import model.SimulationState;

public abstract class MobileObject {

	protected int crossingDuration; // it represents the number of 
									// step where the object takes part in the simulation
	protected int waitingTime;
	
	protected int length;
	protected int height;
	protected Cell position;
	protected List<Cell> objectCoverage; // list of cells from a same mobile object

	public MobileObject(int length, int height, Cell position) {
		this.length = length;
		this.height = height;
		this.position = position;		
	}
	
	
	public MobileObject(int length, int height) {
		this.length = length;
		this.height = height;	
	}
	
	
	// Methods
	/**
	 * Compute the central position of an object.
	 * @param length
	 * @param height
	 * @return central position
	 */

	public Cell computePosition(SimulationState grid) {

		int posx = ((int) length/2) + 1;
		int posy = ((int) height/2) + 1;
		int x = position.getX();
		int y = position.getY();
		
		return grid.getGridValue(x, y);
	}

	/**
	 * Abstract method that gives the type of such objects
	 * @return type of this MobileObject
	 */
	public abstract MobileType getType();

}
