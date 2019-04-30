package immobile.structures;

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
	

	
	
}
