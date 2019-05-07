package mobile;

import enumeration.MobileType;
import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.Profil;
import immobile.structures.Lane;
import immobile.structures.Road;
import immobile.structures.SideWalk;
import model.Cell;

public class Pedestrian extends MobileObject{

	private MovingParts movingParts;
	private double velocity;
	private SideWalk sideWalk;
	OrientedDirection pedestrianDirection;
	
	
	/**
	 * First constructor, with position
	 * @param movingParts
	 * @param position
	 */
	public Pedestrian(MovingParts movingParts, int[] position) {
		
		super(1, 1, position);
		computeCoverage();
		
		this.waitingTime = 0;
		this.crossingDuration = 0;
		this.velocity = 0.8/3.6;
	}
	
	
	
	public Pedestrian(MovingParts movingParts,  int length, int height, OrientedDirection pedestrianDirection, SideWalk sideWalk) {
		
		super(length, height, initializePedestrianPosition(movingParts.getSimulation().getStructureParts().getStructGrid(),
					sideWalk, length, height, pedestrianDirection));
		computeCoverage();
		
		this.waitingTime = 0;
		this.crossingDuration = 0;
		this.velocity = 0.8/3.6;
		this.pedestrianDirection = pedestrianDirection;
	}
	


	static public int[] initializePedestrianPosition(Cell[][] structGrid, SideWalk sideWalk, int length, int height, OrientedDirection pedestrianDirection) {
			
			Cell[][] grid = structGrid;
		
			Road road = sideWalk.getRoad();
			
			int roadPosition = road.getPosition();
			int pedestrianPositionOnRoad = (int) road.getSideWalkSize()/2;
			
			Integer x = Integer.valueOf(-1);
			Integer y = Integer.valueOf(-1);

			switch (pedestrianDirection) {
	        case WE:
	        	y = roadPosition + pedestrianPositionOnRoad ;
	        	x = (int) length/2 + length%2 - 1;
	        	break;
	        case NS:
				x = grid[0].length - roadPosition - pedestrianPositionOnRoad;
				y = (int) length/2 + length%2 - 1; //taking length of pedestrian in account	
				break;
	        case SN:
				x = grid[0].length - roadPosition - pedestrianPositionOnRoad;
				y = grid[0].length - ((int) length/2) - 1; //taking length of pedestrian in account		
				break;
	        case EW:
				y = roadPosition + pedestrianPositionOnRoad;
				x = grid[0].length - ((int) length/2) - 1;
				break;
			}
			System.out.println("initializePedestrianPosition:");
			int[] position = {y, x};
			return position;
		}
	
	
	
	
	
	
	
	
	public void computeCoverage(){
		
		this.objectCoverage.clear();
		
	
		
		
		int x0 = position[0] - (int) length/2;
		int y0 = position[1] - (int) height/2;
		
		System.out.println("origine" + x0 +","+ y0);
		
		for (int i = x0; i < x0 + length; i++) {
			for (int j = y0; j < y0 + height; j++) {
				Integer[] pos = {i, j};
				System.out.println(i + "," + j);
				objectCoverage.add(pos);
			}
		}
		
		Integer[] pos = {this.position[0],this.position[1]};

		objectCoverage.add(pos);
		
		System.out.println();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void nextStep() {
		this.computeCoverage();
		//System.out.println("Car "+this+" looking :"+this.look(10*this.length));
	}
	
	@Override
	public MobileType getType() {
		return MobileType.Pedestrian;
	}
}