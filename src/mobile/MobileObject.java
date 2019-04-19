package mobile;

import java.util.ArrayList;
import java.util.List;

import model.Cell;

public abstract class MobileObject {

	protected int crossingDuration; // it represents the number of 
									// step where the object takes part in the simulation
	protected int waitingTime;
	
	protected int length;
	protected int height;
	protected Cell position;
	protected List<Cell> containedObject; // list of cells from a same mobile object

	
	
	
	// Methods
	/**
	 * Compute the central position of an object.
	 * @param length
	 * @param height
	 * @return central position
	 */
	/**
	public Cell computePosition(int length, int height) {

		int posx = ((int) length/2) + 1;
		int posy = ((int) height/2) + 1;
		int x = position.getX();
		int y = position.getY();
		
		return cell;
	}
	*/
	
	
	
}
