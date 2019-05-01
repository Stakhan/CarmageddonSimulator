package immobile.structures;

import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.StructureType;

public class Lane extends Structure {
	private boolean direction;
	private double speedLimit;
	private Road containingRoad;
	
	public Lane(boolean direction, double speedLimit, Road containingRoad) {
		this.direction = direction;
		this.speedLimit = speedLimit;
		this.containingRoad = containingRoad;
	}
	public Lane(boolean direction, Road containingRoad) {
		this.direction = direction;
		this.containingRoad = containingRoad;
	}


	@Override
	public StructureType getType() {
		return StructureType.Lane;
	}


	// Getters
	public boolean getDirection() {
		return direction;
	}
	public Road getRoad() {
		return containingRoad;
	}
	public OrientedDirection getOrientedDirection() {

		Orientation orientation = containingRoad.getOrientation();
		
		// Compute oriented direction 
		// Reminder : orientation = Vertical or Horizontal
		//			: direction = true or false
		
			if (orientation == Orientation.Horizontal) {
				if (this.direction == true) {
					return OrientedDirection.WE;
				}
				else {
					return OrientedDirection.EW;
				}			
			}
			if (orientation == Orientation.Vertical) {
				if (this.direction == true) {
					return OrientedDirection.NS;
				}
				else {
					return OrientedDirection.SN;
				}			
			}
		
		return null; // because Java needs a return statement :(
	}
	
	

	
	
}
