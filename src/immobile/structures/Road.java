package immobile.structures;

import java.util.ArrayList;
import java.util.List;

import enumeration.Orientation;

public class Road {
	
	private int length;
	private int laneSize;
	private int sideWalkSize;
	private int roadSize;
	private int laneNb;
	private boolean bidirectional;
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
		this.laneNb = laneNb;
		this.bidirectional = bidirectional;
		this.listLanes = new ArrayList<Lane>();
		this.listSideWalks = new ArrayList<SideWalk>();
		
		
		//Creating lanes
		if(!bidirectional) {
			for(int i=0; i<laneNb; i++) {
				listLanes.add(new Lane(true, this));
			}
			this.roadSize = laneSize*laneNb;
		}
		else if (bidirectional) {
			for(int i=0; i<laneNb; i++) {
				listLanes.add(new Lane(false, this));
			}
			for(int i=0; i<laneNb; i++) {
				listLanes.add(new Lane(true, this));
			}
			this.roadSize = 2*laneSize*laneNb;
		}
		
		
		//Creating Sidewalks
		listSideWalks.add(new SideWalk());
		listSideWalks.add(new SideWalk());
		this.roadSize += 2*sideWalkSize;
		
		
		//Computing position from length and size
		this.position = (int) this.length/2 - this.roadSize/2 + 1;
		System.out.println("Road object created.");
		System.out.println("Position of this "+this.orientation+" road is "+this.position);
		
	}
	
	public static Road build() {
		return null;
	}
	
	/*
	 * Getters
	 */
	public int getPosition() {
		return position;
	}
	public int getRoadSize() {
		return roadSize;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	public int getLaneSize() {
		return laneSize;
	}
	public int getSideWalkSize() {
		return sideWalkSize;
	}
	public Lane getLane(int index) {
		return listLanes.get(index);
	}
	public SideWalk getSideWalk(int index) {
		return listSideWalks.get(index);
	}
	public List<Lane> getListLanes() {
		return listLanes;
	}
	public int getLength() {
		return length;	
	}
	public int getIndexOfLane(Lane lane) {
		return listLanes.indexOf(lane);
	}
	
	
}
