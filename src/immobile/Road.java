package immobile;

import java.util.List;

import enumeration.Orientation;

public class Road {
	
	int length;
	int laneSize;
	int sideWalkSize;
	int roadSize;
	int position;
	Orientation orientation;
	List<Lane> listLanes;
	List<SideWalk> listSideWalks;
	
	
	public Road(int length, int laneSize, int sideWalkSize, Orientation orientation, int laneNb) {
		this.length = length;
		this.laneSize = laneSize;
		this.sideWalkSize = sideWalkSize;
		this.orientation = orientation;
		//this.listLanes = new List<Lane>();
		
		//Define lanes
		
	}
	
	
}
