package mobile;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import enumeration.MobileType;
import enumeration.OrientedDirection;
import enumeration.StructureType;
import immobile.structures.Road;
import immobile.structures.SideWalk;
import model.Cell;

public class Pedestrian extends MobileObject{
	

	private MovingParts movingParts;
	private double velocity;
	private SideWalk sideWalk;
	private OrientedDirection pedestrianDirection;
	private List<enumeration.OrientedDirection> path;
	private boolean turningPoints;

	

	
	
	/**
	 * Second constructor, with all details
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
		this.velocity = 0.8/3.6;
		this.pedestrianDirection = pedestrianDir;
		
		this.path = new ArrayList<OrientedDirection>();
		this.path.add(pedestrianDir); //Adding initial direction
		this.nextDirection(); //Adding next direction
		
		this.sideWalk = sideWalk;
		this.movingParts = movingParts;
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
			listPossibleDirections.add(OrientedDirection.EW);
		}
		if(probSN) {
			listPossibleDirections.add(OrientedDirection.EW);
		}
		
		//Separating several probability spans between 0 and 1 for each direction 
		int sectionNumber = (probWE ? 1 : 0)+(probEW ? 1 : 0)+(probNS ? 1 : 0)+(probSN ? 1 : 0); //FYI : this strange code casts booleans to integers
		double sectionSpan = 1/sectionNumber;
		
		Random rand = new Random();
		double decider = rand.nextDouble();
		
		for(int i=0; i < sectionNumber; i++) {
			if(i*sectionSpan <= decider && decider < (i+1)*sectionSpan) {
				return listPossibleDirections.get(i);
			}
		}
		return null; //needed but shouldn't happend
		
	}
	
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
			case SN:
				probNS = false;
			}
			
			//Preventing pedestrian to go back on direction he has already been into
			for(int i=0;i < this.path.size()-1;i++) {
				switch(previous) {
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
			//Adding new direction to path
			this.path.add(pickRandDirection(probWE, probEW, probNS, probSN));

		}
	}
	

public void go() {
		
		
		// the car goes to velocity*0,8 cells per state
		int distance = (int) (this.velocity*3.6/0.8); //int sup

		
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
	

	public void lookTrafficLight() {
		
		Cell[][] grid = this.movingParts.getSimulation().getStructureParts().getStructGrid(); // get the grid of the simulation, to know the position of the different sidewalk
		
		//if (grid[position[1]][position[0]].getContainedLights()[0])
		
		
	}







	public void nextStep() {
		this.computeCoverage();
		
		this.go();
		//this.deviate(0.5);
		//System.out.println("Car "+this+" looking :"+this.look(10*this.length));
	}
	
	@Override
	public MobileType getType() {
		return MobileType.Pedestrian;
	}
}