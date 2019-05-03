package mobile;

import enumeration.MobileType;
import enumeration.OrientedDirection;
import immobile.StructureParts;
import immobile.structures.Road;
import immobile.structures.SideWalk;
import model.Cell;
import model.SimulationState;

public class Pedestrian extends MobileObject {
	private MobileObject mobileObject;
	private SideWalk sideWalk;
	private double velocity;
	private OrientedDirection  pedestrianDirection;
	
	// Basic constructor
	public Pedestrian(SideWalk sideWalk, StructureParts structureParts, double velocity, OrientedDirection  pedestrianDirection, boolean bool) {

		super(1, 1, initializePedestrianPosition(structureParts, sideWalk, bool));
		

		this.sideWalk = sideWalk;
		this.velocity = velocity;
		this.crossingDuration = 0;
		this.waitingTime = 0;
		this.pedestrianDirection = pedestrianDirection;
	}
	
	
	// Constructor 2 : without velocity. In this case, the velocity corresponds to 1 case per iteration (0.8/3.6 m.s-1)
	public Pedestrian(SideWalk sideWalk, StructureParts structureParts, OrientedDirection  pedestrianDirection, boolean bool) {

		super(1, 1, initializePedestrianPosition(structureParts, sideWalk, bool));
		

		this.sideWalk = sideWalk;
		this.velocity = 0.8/3.6;
		this.pedestrianDirection = pedestrianDirection;
		this.crossingDuration = 0;
		this.waitingTime = 0;
	}
	
	
	
	static private Cell initializePedestrianPosition(StructureParts structureParts, SideWalk sideWalk, boolean bool) {
		Cell[][] grid = structureParts.getStructGrid();
		Road road = sideWalk.getRoad();
		
		// Index of pedestrian
		// the pedestrian pop on the top corner of the simulation
		int roadPosition = road.getPosition();
		int pedestrianPositionOnRoad = road.getSideWalkSize();
		if (bool) {
			int x = grid[0].length - roadPosition - pedestrianPositionOnRoad;
			int y = 1; //normal pedestrian
			return grid[x][y];		
		}
		pedestrianPositionOnRoad = road.getSideWalkSize() + road.getLaneSize();
		int x = grid[0].length - roadPosition - pedestrianPositionOnRoad;
		int y = grid.length - 1;
		return grid[x][y];			
	}


	public void go(SimulationState grid) {

		// the car goes to velocity*0,8 cells per state
		int distance = (int) ((int) this.velocity*3.6/0.8);
		
		// Changing the position of the car
		
		// /!\ The case where the pedestrian is out of the grid is not implemented
		
		
		if (pedestrianDirection == OrientedDirection.NS) {
			position.setX(position.getX() + distance);
		}
		if (pedestrianDirection == OrientedDirection.SN) {
			position.setX(position.getX() - distance);
		}
		if (pedestrianDirection == OrientedDirection.EW) {
			position.setX(position.getY() - distance);
		}
		if (pedestrianDirection == OrientedDirection.WE) {
			position.setY(position.getX() + distance);
		}
	}
	
	
	
	
	
	
	
	
	@Override
	public MobileType getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
