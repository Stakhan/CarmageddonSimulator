package mobile;

import enumeration.MobileType;
import enumeration.ObstacleType;
import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.Profil;
import immobile.structures.Lane;
import immobile.structures.Road;
import model.Cell;

public class Car extends MobileObject {
	/**
	 * Provides a representation of a car in the simulation. Attributes describe the car itself. Methods provide its displacement.
	 */
	private MovingParts movingParts;
	private Profil profil;
	private int velocity;
	private int maxVelocity;
	private int maxBrake;
	private Lane lane;
	private Vision vision;
	
	public Car(MovingParts movingParts, int length, int height, Profil profil,
			int velocity, int maxVelocity, int maxBrake, Lane lane) {
		super(length, height, initializeCarPosition(movingParts.getSimulation().getStructureParts().getStructGrid(), lane, length, height));
		
		initializeCarPosition(movingParts.getSimulation().getStructureParts().getStructGrid(), lane, length, height);
		this.movingParts = movingParts;
		
		this.profil = profil;
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;
		this.maxBrake = maxBrake;
		
		this.length = length;
		this.height = height;

		this.lane = lane;
		
		this.crossingDuration = 0;
		this.waitingTime = 0;
		
		// Vision fixe dans un premier temps
		this.vision = new Vision(5 * 4, this);
		
		computeCoverage(movingParts);
	}

	static public int[] initializeCarPosition(Cell[][] structGrid, Lane lane, int length, int height) {
		Cell[][] grid = structGrid;

		Road road = lane.getRoad();
		OrientedDirection carDirection = lane.getOrientedDirection();
		
		// Index of car
		int roadPosition = road.getPosition();
		int carPositionOnRoad = road.getSideWalkSize() + road.getLaneSize()*(road.getIndexOfLane(lane)+1) - ((int) road.getLaneSize()/2 +1);

		int i = 0;
		int j = 0;
		
		switch (carDirection) {
        case WE:
        	i = roadPosition + carPositionOnRoad;
        	j = (int) length/2;
        	break;
        case NS:
        	i = (int) length/2;
        	j = road.getLength() - roadPosition - carPositionOnRoad ;
			break;
        case SN:
        	i = road.getLength() - (int) length/2 - 1;
        	j = road.getLength() - roadPosition - carPositionOnRoad ;
        	break;
        case EW:
        	i = roadPosition + carPositionOnRoad;
        	j = road.getLength() - (int) length/2 - 1;
			break;
		}

		int[] position = {i,j};
		//System.out.println("car position:"+position[0]+","+position[1]);


		return position;
	}
	

	/**
	 * Compute the cells occupied by the current car and list them
	 * @param reference grid
	 */
	private void computeCoverage(MovingParts movingParts) {
		
		this.objectCoverage.clear(); //Clear old coverage
		
		if (visible) {
			Orientation carOrientation = this.lane.getRoad().getOrientation();
			
			int sideSpan = (int) this.height/2;
			int span = (int) this.length/2;

			if (carOrientation == Orientation.Horizontal) {
				int iOrigin = this.position[0] - sideSpan;
				int jOrigin = this.position[1] - span;
				
				for(int i=iOrigin; i<iOrigin+this.height; i++) {
					for(int j=jOrigin; j<jOrigin+this.length; j++) {
						
						// test if the coverage is in the simulation
						int laneLength = lane.getRoad().getLength();
						if (i < laneLength) {
							Integer[] couple = {i,j};
							this.objectCoverage.add(couple);
						}
					}
				}
			}
			
			else if (carOrientation == Orientation.Vertical) {
				int iOrigin = this.position[0] - span;
				int jOrigin = this.position[1] - sideSpan;
				
				for(int i=iOrigin; i<iOrigin+this.length; i++) {
					for(int j=jOrigin; j<jOrigin+this.height; j++) {
						
						// test if the coverage is in the simulation
						int laneLength = lane.getRoad().getLength();
						if (j < laneLength) {
							Integer[] couple = {i,j};
							this.objectCoverage.add(couple);
						}
					}
				}
			}
		}
		
	}
	
	// Methods related to the movement of the car
	

		/**
		 * Compilation of all actions of a car in one step. Calls other methods of this class.
		 */
		public void nextStep() {
			this.crossingDuration += 1;
			this.go();
			this.computeCoverage(this.movingParts);
			this.changeVelocity(true);
			this.vision.setViewSpanDepth(this.velocity * 3); // To have a deeper vision : *3
			this.vision.updateView(this.velocity * 3);
			this.vision.look();
			
			
			if (this.maxVelocity == 0) {
				this.waitingTime += 1;
			}
			
			Obstacle obstacle = this.vision.look();			
			if (obstacle.getType().equals(ObstacleType.Empty) && this.velocity < this.maxVelocity) { // No obstacle

					this.changeVelocity(true);
			}
			else { // Cases : Car, Pedestrian, Red/Orange Traffic Light
				Integer[] obstaclePosition = obstacle.getPosition();
				if(obstacle.getType().equals(ObstacleType.Car)) { //Make sure we aren't going to crash into next car
					if(((Car) obstacle.getObject()).getVelocity() < this.velocity && obstacle.getDistance() < this.velocity) {
						this.velocity = obstacle.getDistance();
					}
				}
				else if(obstacle.getType().equals(ObstacleType.TrafficLight)) {
					if(this.velocity == 0 && obstacle.getDistance() > 2) {
						this.velocity += 1;
					}
					else if (obstacle.getDistance() <=2){
						this.velocity = 0;
					}
					else if (this.velocity > obstacle.getDistance()){
						this.velocity = obstacle.getDistance() - 2;
					}
				}
			}
		
			//System.out.println("viewSpanDepth : " + this.vision.getViewSpanDepth());
			//List<Integer[]> test = this.vision.getViewList();
			//System.out.println("viewSpanList : " + this.vision.getViewList());
			//this.vision.toString();

		}
		
		/**
		 * Changes the velocity of the car depending on the profil of its driver and whether there shall be acceleration or deceleration
		 */
		public void changeVelocity(boolean accelerate) {
			
			int sign = 1; //Positve or negative (accelerate or decelerate)
			if (!accelerate) {
				sign = -1;
			}
			switch (this.profil) {
			case slow:
				this.velocity += sign*3;
				break;
			case respectful:
				this.velocity += sign*5;
	    		break;
			case crazy:
				this.velocity += sign*7;
				break;
			case veryCrazy:
				this.velocity += sign*10;
				break;
			}
			if (this.velocity > maxVelocity) {
				this.velocity = maxVelocity;
			}
			else if (this.velocity < 0) {
				this.velocity = 0;
			}
		}
		
		/**
		 * changes the position of the car
		 */
		public void go() {

			// the car goes to velocity*0,8 cells per state
			int distance = (int) ((int) this.velocity/1);
			
			// Changing the position of the car
			OrientedDirection carDirection = this.lane.getOrientedDirection();
			
			if (!this.inGarage()) { //Test if car is in garage position
				switch (carDirection) {
				case NS: 
					if (position[0] + distance <= this.lane.getRoad().getLength()) { //test if car is still inside of the simulation after movement
						position[0] = position[0] + distance; 
						this.visible = true;
					}
					else { //In case it leaves the simulation
						this.park();
					}

					break;
				case SN:
					if (position[0] - distance >= 0) {
					position[0] = position[0] - distance;
					this.visible = true;
					}
					else { //In case it leaves the simulation
						this.park();
					}
		    		break;
				case EW:
					if (position[1] - distance >= 0) {
					position[1] = position[1] - distance;
					this.visible = true;
					}
					else { //In case it leaves the simulation
						this.park();
					}
		    		break;
				case WE:
					if (position[1] + distance <= this.lane.getRoad().getLength()) {
					position[1] = position[1] + distance;
					this.visible = true;
					}
					else { //In case it leaves the simulation
						this.park();
					}
		    		break;
				}
			}
		}
	

	
	//Getters
	
	@Override
	public MobileType getType() {
		return MobileType.Car;
	}
	
	public Lane getLane() {
		return lane;
	}
	
	public MovingParts getMovingParts() {
		return movingParts;
	}

	public Vision getVision() {
		return this.vision;
	}
	
	public int getVelocity() {
		return velocity;
	}
}
