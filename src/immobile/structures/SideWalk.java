package immobile.structures;

import enumeration.StructureType;
import enumeration.ObstacleType;

public class SideWalk extends Structure {
	
	private Road road;
	
	public SideWalk(Road road) {
		this.road = road;
	}
	
	
	//Getters
	
	public Road getRoad() {
		return road;
	}
	
	
	@Override
	public StructureType getType() {
		return StructureType.SideWalk;
	}

}
