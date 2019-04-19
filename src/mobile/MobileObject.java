package mobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.MobileType;
import enumeration.StructureType;
import model.Cell;

public abstract class MobileObject {

	protected int crossingDuration; // it represents the number of 
									// step where the object takes part in the simulation
	protected int waitingTime;
	
	protected int length;
	protected int height;
	protected int[] position;
	protected List<Cell> containedObject; // list of cells from a same mobile object
	
	
	// Methods
	/**
	 * Compute the central position of an object.
	 * @param length
	 * @param height
	 * @return central position
	 */
	public int[] computePosition(int length, int height) {
		int[] pos = new int [2];
		int posx = ((int) length/2) + 1;
		int posy = ((int) height/2) + 1;
		pos[0] = (posx);
		pos[1] = (posy);		
		return pos;
		
	}
	/**
	 * Abstract method that gives the type of such objects
	 * @return type of this MobileObject
	 */
	public abstract MobileType getType();
}
