package immobile.structures;

import enumeration.MobileType;
import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.StructureType;
import enumeration.ObstacleType;
import mobile.Car;
import model.ConfigureStructure;
import model.SimulationState;

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

	/**
	 * Test if lane entrance is free for spawning a new car
	 * @param length of free space
	 * @param SimulationState on which the test shall be conducted
	 * @return a boolean
	 */
	public boolean testAvailability(int length, SimulationState state) {
		
		int[] coord = Car.initializeCarPosition(state.getGrid(), this, length, 3);
		
		int x = coord[0];
		int y = coord[1];
		switch (this.getOrientedDirection()) {
        case WE:
        	for (int k=y; k<y+(int)length; k++) {
        		//System.out.println(x+","+k+": "+state.getGrid()[x][k].contains(MobileType.Car));
        		if (state.getGrid()[x][k].contains(MobileType.Car) || state.getGrid()[x][k].contains(MobileType.Pedestrian)){
        			return false;
        		}
        	}
        	break;
        case NS:
        	for (int k=x; k<x+(int)length; k++) {
        		if (state.getGrid()[k][y].contains(MobileType.Car) || state.getGrid()[k][y].contains(MobileType.Pedestrian)){
        			return false;
        		}
        	}
			break;
        case SN:
        	for (int k=x; k>x-(int)length; k--) {
        		if (state.getGrid()[k][y].contains(MobileType.Car) || state.getGrid()[k][y].contains(MobileType.Pedestrian)){
        			return false;
        		}
        	}
			break;
        case EW:
        	for (int k=y; k>y-(int)length; k--) {
        		if (state.getGrid()[x][k].contains(MobileType.Car) || state.getGrid()[x][k].contains(MobileType.Pedestrian)){
        			return false;
        		}
        	}
			break;
		}
		
		return true;
	}
	
	@Override
	public StructureType getType() {
		return StructureType.Lane;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------
	//	*** Computing the intersection position ***
	//-----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Compute the intersection position of a lane. Useful to know where the cars need to turn.
	 * Convention :
	 * 0,0 | 1,0
	 * ---------
	 * 1,1 | 0,1
	 * First index : index of the road
	 * Second index : index of the lane
	 * @return
	 */
	
	public int[] getIntersectionPosition() {
		int roadPosition = this.getRoad().getPosition();
		//int pedestrianPositionOnRoad = (int) road.getSideWalkSize()/2;
		
		int x = 0;
		int y = 0;
		
		if (this.getIndex() == 0 && this.getRoad().getIndex() == 0) {
			x = roadPosition + this.getRoad().getLaneSize() + 1;
			y = roadPosition + this.getRoad().getLaneSize();
		}
		else if (this.getIndex() == 0 && this.getRoad().getIndex() == 1) {
			x = roadPosition + this.getRoad().getLaneSize()*2 + 1;
			y = roadPosition + this.getRoad().getLaneSize();
		}
		else if (this.getIndex() == 1 && this.getRoad().getIndex() == 0) {
			x = roadPosition + this.getRoad().getLaneSize() + 1;
			y = roadPosition + this.getRoad().getLaneSize()*2;
		}
		else if (this.getIndex() == 1 && this.getRoad().getIndex() == 1) {
			x = roadPosition + this.getRoad().getLaneSize()*2 + 1;
			y = roadPosition + this.getRoad().getLaneSize()*2;
		}
		int[] position = {y, x};
		return position;
	}
	
	
	// Getters
	public boolean getDirection() {
		return direction;
	}
	public Road getRoad() {
		return containingRoad;
	}
	
	public int getIndex() {
		return this.containingRoad.getIndexOfLane(this);
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
