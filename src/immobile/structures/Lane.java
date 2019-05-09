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
		int roadPosition = containingRoad.getPosition();
 		int carPositionOnRoad = getCarPositionOnRoad();
 		
		int x = coord[0];
		int y = coord[1];
		switch (this.getOrientedDirection()) {
        case WE:
        	for (int k=x; k<x+(int)length/2; k++) {
        		System.out.println(k+","+y+": "+state.getGrid()[y][k].contains(MobileType.Car));
        		if (state.getGrid()[y][k].contains(MobileType.Car) || state.getGrid()[y][k].contains(MobileType.Pedestrian)){
        			return false;
        		}
        	}
        	break;
        case NS:
        	for (int k=y; k<y+(int)length/2; k++) {
        		if (state.getGrid()[k][x].contains(MobileType.Car) || state.getGrid()[k][x].contains(MobileType.Pedestrian)){
        			return false;
        		}
        	}
			break;
        case SN:
        	for (int k=y; k>y-(int)length/2; k--) {
        		if (state.getGrid()[k][x].contains(MobileType.Car) || state.getGrid()[k][x].contains(MobileType.Pedestrian)){
        			return false;
        		}
        	}
			break;
        case EW:
        	for (int k=x; k>x-(int)length/2; k--) {
        		if (state.getGrid()[y][k].contains(MobileType.Car) || state.getGrid()[y][k].contains(MobileType.Pedestrian)){
        			System.out.println("yik");
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

	public int getCarPositionOnRoad() {
		return containingRoad.getSideWalkSize() + containingRoad.getLaneSize()*(containingRoad.getIndexOfLane(this)+1) - ((int) containingRoad.getLaneSize()/2);
	}

	// Getters
	public boolean getDirection() {
		return direction;
	}
	public Road getRoad() {
		return containingRoad;
	}
	public OrientedDirection getOrientedDirection() {
		OrientedDirection orientedDirection = null;
		Orientation orientation = containingRoad.getOrientation();

		// Compute oriented direction 
		// Reminder : orientation = Vertical or Horizontal
		//			: direction = true or false
		
			if (orientation == Orientation.Horizontal) {
				if (this.direction) {
					orientedDirection = OrientedDirection.WE;
				}
				else {
					orientedDirection =  OrientedDirection.EW;
				}			
			}
			else if (orientation == Orientation.Vertical) {
				if (this.direction) {
					orientedDirection = OrientedDirection.NS;
				}
				else {
					orientedDirection = OrientedDirection.SN;
				}			
			}
		
		return orientedDirection; // because Java needs a return statement :(
	}
	
	

	
	
}
