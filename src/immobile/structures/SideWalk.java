package immobile.structures;

import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.StructureType;
import enumeration.ObstacleType;

public class SideWalk extends Structure {
	

	private Road containingRoad;
	
	
	public SideWalk(Road containingRoad) {
		this.containingRoad = containingRoad;
	}

	
	// Getters
	
	public Road getRoad() {
		return containingRoad;
	}
	
	public int getIndex() {
		return this.containingRoad.getListSideWalks().indexOf(this);
	}
	

	
	@Override
	public StructureType getType() {
		return StructureType.SideWalk;
	}

}
