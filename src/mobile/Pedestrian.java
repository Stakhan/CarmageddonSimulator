package mobile;


import java.util.List;

import enumeration.Color;
import enumeration.MobileType;
import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.Profil;
import enumeration.StructureType;
import immobile.structures.Lane;
import immobile.structures.Road;
import immobile.structures.SideWalk;
import model.Cell;

public class Pedestrian extends MobileObject{

	private MovingParts movingParts;
	private double velocity;
	private SideWalk sideWalk;
	private OrientedDirection pedestrianDirection;
	private boolean hasAlreadyTurned; // this boolean is used to know if a pedestrian already turned on a bigger crossing section. Useful for crossing methods
	private List<enumeration.OrientedDirection> path;

	
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
		this.movingParts = movingParts;
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
		this.velocity = 1;
		this.pedestrianDirection = pedestrianDirection;
		this.sideWalk = sideWalk;
		this.movingParts = movingParts;
		this.hasAlreadyTurned = false;
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
		this.velocity = 1;
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
	
	
	
	
	
	
	
	/**
	 * Compute the coverage of a pedestrian
	 * Only for square pedestrian
	 */
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
		
		
		// the pedestrian goes to velocity*0,8 cells per state
		int distance = (int) (this.velocity/1); //int sup

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
	 * Deviate methods, it moves the position of the pedestrian to another case, only on a sidewalk
	 * @param proba : the probability to deviate
	 */
	public void deviate(double proba) {

		Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid(); // get the grid of the simulation, to know the position of the different sidewalk
			
		int x = position[0];
		int y = position[1];
		double random = Math.random();
		
		if (random < proba) {
			if ((pedestrianDirection == OrientedDirection.WE)||(pedestrianDirection == OrientedDirection.EW)) {
				if ((grid[x + (int) length/2 + 1][y].contains(StructureType.SideWalk))&&(grid[x - (int) length/2 - 1][y].contains(StructureType.SideWalk))) {
					double rd = Math.random();
					if (rd < 0.5) {
						System.out.println(Math.random());
						position[0] += 1;
					}
					else {
						System.out.println(true);
						position[0] -= 1;
					}
				}
				
				if (grid[x + (int) length/2 + 1][y].contains(StructureType.SideWalk)) {
					position[0] += 1;
				}
				if (grid[x - (int) length/2 - 1][y].contains(StructureType.SideWalk)) {
					position[0] -= 1;
				}
			} // end
			
			if ((pedestrianDirection == OrientedDirection.NS)||(pedestrianDirection == OrientedDirection.SN)) {
				if ((grid[x][y + (int) length/2 + 1].contains(StructureType.SideWalk))&&(grid[x][y - (int) length/2 - 1].contains(StructureType.SideWalk))) {
					double rd = Math.random();
					if (rd < 0.5) {
						System.out.println(Math.random());
						position[1] += 1;
					}
					else {
						System.out.println(true);
						position[1] -= 1;
					}
				}
				
				if (grid[x][y + (int) length/2 + 1].contains(StructureType.SideWalk)) {
					position[1] += 1;
				}
				if (grid[x][y - (int) length/2 - 1].contains(StructureType.SideWalk)) {
					position[1] -= 1;
				}
			} // end
		} // end if proba

	}
	
	
	/**
	 * Getter, return the orientation of the pedestrian
	 * @return Orientation
	 */
	public Orientation getOrientation() {
		// get the orientation of the pedestrian, to know which traffic light he needs to take into account
		Orientation orientation = Orientation.Vertical;
		if ((pedestrianDirection == OrientedDirection.NS)||(pedestrianDirection == OrientedDirection.SN)) {
			orientation = Orientation.Horizontal;
		}
		return orientation;
	}
	
	
	/**
	 * This method compute the local position of a pedestrian, to know if he is at a crossing section
	 * @return boolean : true if he is at a crossing section
	 */
	public boolean isAtCrossingSection() {
		Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid(); // get the grid of the simulation, to know the position of the different sidewalk
		if (grid[position[0]][position[1]].getContainedLights().size() != 0) {
			return true;
		}
		return false;		
	}
	
	
	/**
	 * This methods is used to know if the next movement of the pedestrian will be on a pedestrian crossing, or if he stays on the side walk.
	 * @return boolean : true the pedestrian will cross the road
	 */
	public boolean pedestrianCrossing() {
		Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid();
		// get the grid of the simulation, to know the position of the different sidewalk
		if (isAtCrossingSection()) {
			// if he is at a crossing section
			int distance = (int) (this.velocity/1); // compute his distance
			switch(pedestrianDirection) {
			case NS:
				if (grid[position[0] + distance][position[1]].contains(StructureType.Lane)) {
					// if the next case is a road
					return true;
				}
				break;
			case SN:
				if (grid[position[0] - distance][position[1]].contains(StructureType.Lane)) {
					return true;
				}
				break;
			case WE:
				if (grid[position[0]][position[1] + distance].contains(StructureType.Lane)) {
					return true;
				}
				break;
			case EW:
				if (grid[position[0]][position[1] - distance].contains(StructureType.Lane)) {
					return true;
				}
				break;
			}
		}
		return false;
	}


	/**
	 * This methods is used to know if the pedestrian needs to stop and wait at a crossing section.
	 * In that case, the crossing duration is taking into account.
	 * @return boolean : true the pedestrian needs to stop
	 */
	public boolean stop() {
		Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid();
		
		if (isAtCrossingSection()) {
			if (pedestrianCrossing()) {
				System.out.println(true);
				Orientation orientation = getOrientation();
				switch (orientation) {
				case Vertical:
					if (grid[position[0]][position[1]].getContainedLights().get(0).getCurrentColor() == Color.Red) {
						return true;
					}
					break;
				case Horizontal:
					if (grid[position[0]][position[1]].getContainedLights().get(1).getCurrentColor() == Color.Red) {
						return true;
					}
					break;
				} // end switch
			}
		}
		return false;
	}




	public void nextStep() {
		this.computeCoverage();
		if (!stop()) {
			this.go();
		}
		//this.go();
		if (!isAtCrossingSection()) {
			this.deviate(0.5);
		}

		//System.out.println("Car "+this+" looking :"+this.look(10*this.length));
	}
	
	@Override
	public MobileType getType() {
		return MobileType.Pedestrian;
	}
}