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
	
	
	/**
	 * Second constructor, with all details
	 * @param movingParts
	 * @param length
	 * @param height
	 * @param pedestrianDirection
	 * @param sideWalk
	 */
	public Pedestrian(MovingParts movingParts,  int length, int height, OrientedDirection pedestrianDirection, SideWalk sideWalk) {
		
		super(length, height, initializePedestrianPosition(movingParts.getSimulation().getStructureParts().getStructGrid(),
					sideWalk, length, height, pedestrianDirection));
		computeCoverage();
		
		this.waitingTime = 0;
		this.crossingDuration = 0;
		this.velocity = 0.8/3.6;
		this.pedestrianDirection = pedestrianDirection;
		this.sideWalk = sideWalk;
	}
	
	
	/**
	 * Third constructor, same as 2nd without size
	 * @param movingParts
	 * @param length
	 * @param height
	 * @param pedestrianDirection
	 * @param sideWalk
	 */
	public Pedestrian(MovingParts movingParts, OrientedDirection pedestrianDirection, SideWalk sideWalk) {
		
		super(1, 1, initializePedestrianPosition(movingParts.getSimulation().getStructureParts().getStructGrid(),
					sideWalk, 1, 1, pedestrianDirection));
		computeCoverage();
		
		this.waitingTime = 0;
		this.crossingDuration = 0;
		this.velocity = 0.8/3.6;
		this.pedestrianDirection = pedestrianDirection;
		this.length = 1;
		this.height = 1;
		this.sideWalk = sideWalk;
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
			//System.out.println("initializePedestrianPosition:");
			int[] position = {y, x};
			return position;
		}
	
	
	
	
	
	
	
	
	public void computeCoverage(){
	
		//Only for square pedestrian
		
		this.objectCoverage.clear();

		int x0 = position[0] - (int) length/2;
		int y0 = position[1] - (int) height/2;
		//System.out.println("origine" + x0 +","+ y0);
		for (int i = x0; i < x0 + length; i++) {
			for (int j = y0; j < y0 + height; j++) {
				Integer[] pos = {i, j};
				//System.out.println(i + "," + j);
				objectCoverage.add(pos);
			}
		}
		
		Integer[] pos = {this.position[0],this.position[1]};
		objectCoverage.add(pos);
		
		//System.out.println();
	}
	
	
	

public void go() {
		
		
		// the car goes to velocity*0,8 cells per state
		int distance = (int) (this.velocity*3.6/0.8) + 1; //int sup

		
		if (!this.inGarage()) { //Test if a pedestrian is in garage position
			switch (pedestrianDirection) {
			case NS: 
				if (position[0] + distance <= this.sideWalk.getRoad().getLength()) { //test if a pedestrian is still inside of the simulation after movement
					position[0] = position[0] + distance; 
					this.visible = true;
				}
				else {
					this.park();
				}
				
				break;
			case SN:
				if (position[0] - distance >= 0) {
				position[0] = position[0] - distance;
				this.visible = true;
				}
				else {
					this.park();
				}
	    		break;
			case EW:
				if (position[1] - distance >= 0) {
				position[1] = position[1] - distance;
				this.visible = true;
				}
				else {
					this.park();
				}
	    		break;
			case WE:
				if (position[1] + distance <= this.sideWalk.getRoad().getLength()) {
				position[1] = position[1] + distance;
				this.visible = true;
				}
				else {
					this.park();
				}
	    		break;
			}
		}
	}
	
	
	/**
	public void deviate() {
		switch (pedestrianDirection) {
		case NS:
			if (height < this.sideWalk.getRoad().getSideWalkSize()) {
				if position[1]
			}
		}
	}
	*/












	
	public void nextStep() {
		this.computeCoverage();
		this.go();
		//System.out.println("Car "+this+" looking :"+this.look(10*this.length));
	}
	
	@Override
	public MobileType getType() {
		return MobileType.Pedestrian;
	}
}