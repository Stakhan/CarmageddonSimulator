package immobile.structures;

import java.util.ArrayList;
import java.util.List;

import enumeration.Orientation;

public class Road {
	
	private int length;
	private int laneSize;
	private int sideWalkSize;
	private int roadSize;
	public int position;
	private Orientation orientation;
	private List<Lane> listLanes;
	private List<SideWalk> listSideWalks;
	
	/**
	 * Constructeur
	 * 
	 * @param length
	 * @param laneSize
	 * @param sideWalkSize
	 * @param orientation
	 * @param laneNb, represents the number of lanes, must be even if road is bidirectional
	 * @param bidirectional
	 */
	public Road(int length, int laneSize, int sideWalkSize, Orientation orientation, int laneNb, boolean bidirectional) {
		this.length = length;
		this.laneSize = laneSize;
		this.sideWalkSize = sideWalkSize;
		this.orientation = orientation;
		this.listLanes = new ArrayList<Lane>();
		this.listSideWalks = new ArrayList<SideWalk>();
		
		//Creating lanes
		if(!bidirectional) {
			for(int i=0; i<laneNb; i++) {
				listLanes.add(new Lane(true));
			}
			this.roadSize = laneSize*laneNb;
		}
		else if (bidirectional) {
			for(int i=0; i<laneNb; i++) {
				listLanes.add(new Lane(false));
			}
			for(int i=0; i<laneNb; i++) {
				listLanes.add(new Lane(true));
			}
			this.roadSize = 2*laneSize*laneNb;
		}
		
		//Creating Sidewalks
		listSideWalks.add(new SideWalk());
		listSideWalks.add(new SideWalk());
		this.roadSize += 2*sideWalkSize;
		
		//Computing position from length and size
		this.position = (int) this.length/this.roadSize/2+1;
		
	}
	
	
}
