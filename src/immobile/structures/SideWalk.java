package immobile.structures;

import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.StructureType;

public class SideWalk extends Structure {
	
	private Road containingRoad;
	
	
	public SideWalk(Road containingRoad) {
		this.containingRoad = containingRoad;
	}

	
	// Getters
		public Road getRoad() {
			return containingRoad;
		}
	
	
	
	@Override
	public StructureType getType() {
		return StructureType.SideWalk;
	}

}
