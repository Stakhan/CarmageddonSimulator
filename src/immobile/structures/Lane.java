package immobile.structures;

import enumeration.StructureType;

public class Lane extends Structure {
	private boolean direction;
	private double speedLimit;

	
	public Lane(boolean direction, double speedLimit) {
		this.direction = direction;
		this.speedLimit = speedLimit;
	}
	public Lane(boolean direction) {
		this.direction = direction;
	}

	@Override
	public StructureType getType() {
		return StructureType.Lane;
	}

	
	
}
