package mobile;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import enumeration.TrafficColor;
import enumeration.MobileType;
import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.StructureType;
import immobile.StructureParts;
import immobile.structures.Road;
import immobile.structures.SideWalk;
import immobile.structures.Structure;
import model.Cell;

public class Pedestrian extends MobileObject{
	

	private MovingParts movingParts;
	private double velocity;
	private SideWalk sideWalk;
	private OrientedDirection pedestrianDirection;
	private boolean hasChangedDirection; // this boolean is used to know if a pedestrian already turned on a bigger crossing section. Useful for turn() and nextStep() methods
	private List<enumeration.OrientedDirection> path;
	private int indexOfPath; // not used currently

	
	
	/**
	 * Main constructor. It initialize probabilistic parameters to 0, and set the velocity of a pedestrian to 1.
	 * @param movingParts
	 * @param length
	 * @param height
	 * @param pedestrianDirection
	 * @param sideWalk
	 */
	public Pedestrian(MovingParts movingParts,  int length, int height, OrientedDirection pedestrianDir, SideWalk sideWalk) {
		
		super(length, height, initializePedestrianPosition(movingParts.getSimulation().getStructureParts().getStructGrid(),
					sideWalk, length, height, pedestrianDir));
		computeCoverage();
		this.waitingTime = 0;
		this.crossingDuration = 0;
		this.velocity = 1;
		this.sideWalk = sideWalk;
		this.movingParts = movingParts;
		this.pedestrianDirection = pedestrianDir;
		this.path = new ArrayList<OrientedDirection>();
		this.path.add(pedestrianDir); //Adding initial direction
		this.nextDirection(); //Adding next direction
		this.sideWalk = sideWalk;
		this.movingParts = movingParts;
		this.hasChangedDirection = false;
	}


	/**
	 * Initialize the position of a pedestrian. Depending of his size and direction, it sets the pedestrian in the middle of the sidewalk.
	 * The position is determined with the direction of a pedestrian, so an initialized pedestrian is forced to travel.
	 * @param structGrid
	 * @param sideWalk
	 * @param length
	 * @param height
	 * @param pedestrianDirection
	 * @return
	 */
	static public int[] initializePedestrianPosition(Cell[][] structGrid, SideWalk sideWalk, int length, int height, OrientedDirection pedestrianDirection) {
			
			Cell[][] grid = structGrid;
		
			Road road = sideWalk.getRoad();

			int roadPosition = road.getPosition();
			int pedestrianPositionOnRoad = (int) road.getSideWalkSize()/2;
			
			Integer x = Integer.valueOf(-1);
			Integer y = Integer.valueOf(-1);

			if(sideWalk.getIndex() == 0) {
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
			}
			else if(sideWalk.getIndex() == 1) {
				switch (pedestrianDirection) {
		        case WE:
		        	x = (int) length/2 + length%2 - 1;
		        	y = roadPosition + road.getSideWalkSize() + road.getLaneSize()*road.getListLanes().size() + pedestrianPositionOnRoad;
		        	break;
		        case NS:
					x = grid[0].length - roadPosition - road.getSideWalkSize() - road.getLaneSize()*road.getListLanes().size() - pedestrianPositionOnRoad;
					y = (int) length/2 + length%2 - 1; //taking length of pedestrian in account	
					break;
		        case SN:
					x = grid[0].length - roadPosition - road.getSideWalkSize() - road.getLaneSize()*road.getListLanes().size() - pedestrianPositionOnRoad;
					y = grid[0].length - ((int) length/2) - 1; //taking length of pedestrian in account		
					break;
		        case EW:
					y = roadPosition + road.getSideWalkSize() + road.getLaneSize()*road.getListLanes().size() + pedestrianPositionOnRoad;
					x = grid[0].length - ((int) length/2) - 1;
					break;
				}
			}
			//System.out.println("initializePedPosition:"+y+","+x+" et "+grid[x][y].getX()+","+grid[x][y].getY());
			int[] position = {y, x};
			return position;
		}
	
	
	
	
	
	
	
	/**
	 * Compute the coverage of a pedestrian.
	 * Only for square pedestrian.
	 */
	public void computeCoverage(){
	
		//Only for square pedestrian
		
		this.objectCoverage.clear();

		int x0 = position[0] - (int) length/2;
		int y0 = position[1] - (int) height/2;
		for (int i = x0; i < x0 + length; i++) {
			for (int j = y0; j < y0 + height; j++) {
				Integer[] pos = {i, j};
				objectCoverage.add(pos);
			}
		}
		Integer[] pos = {this.position[0],this.position[1]};
		objectCoverage.add(pos);	
	}
	
	
	/**
	 * Pick a random direction depending on his direction. A pedestrian can't go back.
	 * @param probWE
	 * @param probEW
	 * @param probNS
	 * @param probSN
	 * @return
	 */
	public OrientedDirection pickRandDirection(boolean probWE, boolean probEW, boolean probNS, boolean probSN) {
		
		//Producing a list of possible directions
		List<OrientedDirection> listPossibleDirections = new ArrayList<OrientedDirection>();
		if(probWE) {
			listPossibleDirections.add(OrientedDirection.WE);
		}
		if(probEW) {
			listPossibleDirections.add(OrientedDirection.EW);
		}
		if(probNS) {
			listPossibleDirections.add(OrientedDirection.NS);
		}
		if(probSN) {
			listPossibleDirections.add(OrientedDirection.SN);
		}
		//Separating several probability spans between 0 and 1 for each direction 
		int sectionNumber = (probWE ? 1 : 0)+(probEW ? 1 : 0)+(probNS ? 1 : 0)+(probSN ? 1 : 0); //FYI : this strange code casts booleans to integers
		double sectionSpan = 1.0/sectionNumber;
		Random rand = new Random();
		double decider = rand.nextDouble();
		
		for(int i=0; i < sectionNumber; i++) {
			if(i*sectionSpan <= decider && decider < (i+1)*sectionSpan) {
				return listPossibleDirections.get(i);
			}
		}
		return null; //needed but shouldn't happend
		
	}
	
	
	/**
	 * Add a random direction to the path.
	 * It test previous direction, so a pedestrian can't go back.
	 */
	public void nextDirection() {
		
		boolean probWE = true;
		boolean probEW = true;
		boolean probNS = true; 
		boolean probSN = true;
		
		if (this.path.size() == 0) { //Init case
			this.path.add(pickRandDirection(probWE, probEW, probNS, probSN));
		}
		else if (this.path.size() != 0){

			//Preventing pedestrian to come back on its path
			OrientedDirection previous = this.path.get(this.path.size()-1);
			switch(previous) {
			case WE:
				probEW = false;
				break;
			case EW:
				probWE = false;
				break;
			case NS:
				probSN = false;
				break;
			case SN:
				probNS = false;
				break;
			}
			
			if (this.path.size() > 1) {
				//Preventing pedestrian to go back on direction he has already been into
				for(int i=0;i < this.path.size()-1;i++) {
					switch(this.path.get(i)) {
					case WE:
						probEW = false;
						break;
					case EW:
						probWE = false;
						break;
					case NS:
						probSN = false;
					case SN:
						probNS = false;
					}
			}
			
			}
			//Adding new direction to path
			this.path.add(pickRandDirection(probWE, probEW, probNS, probSN));

		}
	}
	
	/**
	 * Methods that moves a pedestrian. It changes his position, and put him in the garage if he is outside of the simulation.
	 */
	public void go() {

		if (!this.inGarage()) { //Test if a pedestrian is in garage position
			int distance = (int) (this.velocity/1); //int sup
			
			switch (pedestrianDirection) {
			case NS: 
				if (position[0] + distance <= this.sideWalk.getRoad().getLength() - 1) { //test if a pedestrian is still inside of the simulation after movement
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
				if (position[1] + distance <= this.sideWalk.getRoad().getLength() - 1) {
				position[1] = position[1] + distance;
				this.visible = true;
				}
				else {
					this.park();
				}
	    		break;
			}
			
			// if the pedestrian is on a crossing section, we update the crossingDuration
			//Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid(); // get the grid of the simulation, to know the position of the different sidewalk
			
			//if (grid[position[0] - 1][position[1] - 1].contains(StructureType.Lane)) {
			//	crossingDuration += 1;
			//}
		crossingDuration += 1;
		}
	}
	
	



	/**
	 * Deviate methods, it moves the position of the pedestrian to another case, only on a sidewalk.
	 * @param proba : the probability to deviate
	 */
	public void deviate(double proba) {
		if (!inGarage()) {
			Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid(); // get the grid of the simulation, to know the position of the different sidewalk
			
			int x = position[0];
			int y = position[1];
			double random = Math.random();
			
			if (random < proba) {
				if ((pedestrianDirection == OrientedDirection.WE)||(pedestrianDirection == OrientedDirection.EW)) {
					if ((grid[x + (int) length/2 + 1][y].contains(StructureType.SideWalk))&&(grid[x - (int) length/2 - 1][y].contains(StructureType.SideWalk))) {
						double rd = Math.random();
						if (rd < 0.5) {
							position[0] += 1;
						}
						else {
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
							position[1] += 1;
						}
						else {
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
	}
	
	
	/**
	 * Getter, return the orientation of the pedestrian.
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
	 * This method tells if a pedestrian is at a crossing section.
	 * @return boolean : true if he is at a crossing section.
	 */
	public boolean isAtCrossingSection() {

		if(!inGarage()) {
			Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid(); // get the grid of the simulation, to know the position of the different sidewalk
			if (grid[position[0]][position[1]].getContainedLights().size() != 0) {
				return true;
			}
			return false;
		}
		return false;		
	}
	
	
	
	
	
	/**
	 * This methods is used to know if the next movement of the pedestrian will be on a pedestrian crossing, or if he stays on the side walk.
	 * @return boolean : true the pedestrian will cross the road.
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
	 * @return boolean : true the pedestrian needs to stop.
	 */
	public boolean stop() {
		Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid();
		
		if (isAtCrossingSection()) {
			if (pedestrianCrossing()) {
				Orientation orientation = getOrientation();
				switch (orientation) {
				case Vertical:
					if (grid[position[0]][position[1]].getContainedLights().get(0).getCurrentColor() == TrafficColor.Red) {
						waitingTime += 1;
						return true;
					}
					break;
				case Horizontal:
					if (grid[position[0]][position[1]].getContainedLights().get(1).getCurrentColor() == TrafficColor.Red) {
						waitingTime += 1;
						return true;
					}
					break;
				} // end switch
			}
		}
		return false;
	}

	/**
	 * Each node gets an index from 1 to 4, starting from top left and going clockwise. Gives the node index on which the pedestrian lies. 
	 * @return node index (zero if not on a node).
	 */
	public int whatNode() {
		
		//Extracting list of SideWalk from cell of current position
		List<Structure> listStructures = new ArrayList<Structure>();
		listStructures = this.sideWalk.getRoad().getStructureParts().getStructGrid()[this.position[0]][this.position[1]].getContainedStructures();
		List<SideWalk> listSideWalks = new ArrayList<SideWalk>();
		for(Structure struct: listStructures) {
			if(struct.getType() == StructureType.SideWalk) {
				listSideWalks.add((SideWalk) struct);
			}
		}
		
		//Affecting index depending on sidewalk association
		if(listSideWalks.size() >1) {
			
			int road0 = listSideWalks.get(0).getIndex();
			int road1 = listSideWalks.get(1).getIndex();
			
			if(road0 == 0 && road1 == 1) {
				return 1;
			}
			else if (road0 == 0 && road1 == 0) {
				return 2;
			}
			else if (road0 == 1 && road1 == 0) {
				return 3;
			}
			else if (road0 == 1 && road1 == 1) {
				return 4;
			}
		}
		return 0; //In case pedestrian isn't on a node
	}
	
	
	/**
	 * Methods to change the sidewalk of a pedestrian.
	 */
	public void turn() {
		
		OrientedDirection nextDirection = this.path.get(this.path.size()-1);
		int indexSideWalk = this.sideWalk.getIndex();
		int node = this.whatNode();
		
		if (node == 1) {
			if (nextDirection == OrientedDirection.WE || nextDirection == OrientedDirection.EW) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(0).getListSideWalks().get(0);
			}
			else if (nextDirection == OrientedDirection.NS || nextDirection == OrientedDirection.SN) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(1).getListSideWalks().get(1);
			}
		}
		else if (node == 2) {
			if (nextDirection == OrientedDirection.WE || nextDirection == OrientedDirection.EW) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(0).getListSideWalks().get(0);
			}
			else if (nextDirection == OrientedDirection.NS || nextDirection == OrientedDirection.SN) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(1).getListSideWalks().get(0);
			}
		}
		else if (node == 3) {
			if (nextDirection == OrientedDirection.WE || nextDirection == OrientedDirection.EW) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(0).getListSideWalks().get(1);
			}
			else if (nextDirection == OrientedDirection.NS || nextDirection == OrientedDirection.SN) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(1).getListSideWalks().get(0);
			}
		}
		else if (node == 4) {
			if (nextDirection == OrientedDirection.WE || nextDirection == OrientedDirection.EW) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(0).getListSideWalks().get(1);
			}
			else if (nextDirection == OrientedDirection.NS || nextDirection == OrientedDirection.SN) {
    			this.sideWalk = this.sideWalk.getRoad().getStructureParts().getRoad(1).getListSideWalks().get(1);
			}
		}
		
		this.pedestrianDirection = this.path.get(this.path.size()-1); //Change Pedestrian's direction
	}

	/**
	 * Compute the next state of a pedestrian. It takes into account his decision to turn, wait, go and deviate.
	 */
	public void nextStep() {
		//System.out.println(this+":"+this.path);
		this.computeCoverage();
		
		if(isAtCrossingSection() && hasChangedDirection == false) {				
			this.turn();
			this.nextDirection(); //Add next direction to path
			this.hasChangedDirection = true;
		}
		else if(!isAtCrossingSection() && hasChangedDirection == true) {
			this.hasChangedDirection = false;
		}
		if (!stop()) {
			this.go();
		}
		if (!isAtCrossingSection()) {
			this.deviate(0.5);
		}
	}
	
	// GETTERS
	
	
	
	@Override
	public MobileType getType() {
		return MobileType.Pedestrian;
	}
	
//	public static void main(String[] args) {
//		System.out.println(Pedestrian.pickRandDirection(true,false,false,false));
//	}
}