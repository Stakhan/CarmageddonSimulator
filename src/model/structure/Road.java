package model.structure;

import java.util.List;

public class Road {
	
	int length;
	int laneSize;
	int sideWalkSize;
	int roadSize;
	int position;
	Orientation orientation;
	List<Lane> listLanes;
	List<SideWalk> listSideWalks;
	
	
	public Road(int length, int laneSize, int sideWalkSize, Orientation orientation) {
		this.length = length;
		this.laneSize = laneSize;
		this.sideWalkSize = sideWalkSize;
		this.orientation = orientation;
	}
	
	
}
