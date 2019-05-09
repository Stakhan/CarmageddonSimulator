package mobile;


import enumeration.ObstacleType;
import enumeration.OrientedDirection;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import engine.Simulation;
import enumeration.MobileType;
import enumeration.Orientation;
import enumeration.Profil;
import immobile.StructureParts;
import immobile.structures.Lane;
import immobile.structures.Road;
import model.Cell;
import model.ConfigureStructure;


public class Car extends MobileObject {
	
	private MovingParts movingParts;
	private Profil profil;
	private int maxChange;
	private int velocity;
	private String model;
	private int maxVelocity;
	private int maxBrake;
	private Lane lane;
	private Lane destination;
	private int[] intersectionPosition;
	private Vision vision;

	
	/**UP-TO-DATE
	 * 		
	 * Constructor
	 * 
	 * @param model
	 * @param length
	 * @param height
	 * @param profil
	 * @param velocity
	 * @param maxVelocity
	 * @param maxBrake
	 * @param lane
	 * @param structureParts
	 */
	public Car(MovingParts movingParts, String model, int length, int height, Profil profil,
			int velocity, int maxVelocity, Lane lane) {
	
		super(length, height, initializeCarPosition(movingParts.getSimulation().getStructureParts().getStructGrid(), lane, length, height));
		
		this.movingParts = movingParts;
		
		this.profil = profil;
		this.velocity = velocity;
		this.model = model;
		this.maxVelocity = maxVelocity;
		this.maxBrake = maxBrakeCalculation();
		
		switch (this.profil) {
		case slow:
			maxChange = 3;
			break;
		case respectful:
			maxChange = 5;
    		break;
		case crazy:
			maxChange = 7;
			break;
		case veryCrazy:
			maxChange = 10;
			break;
		}
		
		this.length = length;
		this.height = height;

		this.lane = lane;
		
		this.crossingDuration = 0;
		this.waitingTime = 0;
		
		this.vision = new Vision(this);
		this.vision.update();
		
		this.destination = this.destinationRandom();
		this.intersectionPosition = intersectionCalculation();
		
		computeCoverage(movingParts);
	}
	
	
	/**
	 * 
	 * Set the position of the car at creation depending on which lane it is linked to
	 * 
	 * @param structGrid
	 * @param lane
	 * @param length
	 * @param height
	 * @return
	 */
	static public int[] initializeCarPosition(Cell[][] structGrid, Lane lane, int length, int height) {
		
		Cell[][] grid = structGrid;

		Road road = lane.getRoad();
		OrientedDirection carDirection = lane.getOrientedDirection();
		
		// Index of car
		int roadPosition = road.getPosition();
		int carPositionOnRoad = lane.getCarPositionOnRoad();
		
		Integer x = Integer.valueOf(-1);
		Integer y = Integer.valueOf(-1);
		
		switch (carDirection) {
        case WE:
        	y = roadPosition + carPositionOnRoad;
        	x = (int) length/2 + length%2;
        	break;
        case NS:
			roadPosition -= 1;
			carPositionOnRoad -= 1;
			x = grid[0].length - roadPosition - carPositionOnRoad;
			y = (int) length/2 + length%2; //taking length of car in account
			break;
        case SN:
			roadPosition -= 1;
			carPositionOnRoad -=1;
			x = grid[0].length - roadPosition - carPositionOnRoad;
			y = grid.length - (((int) length/2));
			break;
        case EW:
			y = roadPosition + carPositionOnRoad;
			x = grid[0].length - ((int) length/2);
			break;
		}
		System.out.println("initializeCarPosition:"+x+","+y+" et "+grid[x][y].getX()+","+grid[x][y].getY());
		int[] position = {x,y};
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
			
			int sideSpan = (int) this.height/2 + this.height%2;
			int span = (int) this.length/2 + this.length%2;

			if (carOrientation == Orientation.Horizontal) {
				int xOrigin = this.position[0] - span;
				int yOrigin = this.position[1] - sideSpan;
				
				for(int i=xOrigin; i<xOrigin+this.length; i++) {
					for(int j=yOrigin; j<yOrigin+this.height; j++) {
						Integer[] couple = {j,i};
						this.objectCoverage.add(couple);
					}
				}
			}
			else if (carOrientation == Orientation.Vertical) {
				int xOrigin = this.position[0] - sideSpan;
				int yOrigin = this.position[1] - span;
				
				for(int i=xOrigin; i<xOrigin+this.height; i++) {
					for(int j=yOrigin; j<yOrigin+this.length; j++) {
						Integer[] couple = {j,i};
						this.objectCoverage.add(couple);
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
		this.go();
		this.computeCoverage(this.movingParts);
		this.changeVelocity(true);
		this.vision.update();
//		System.out.println(this+" position: "+position[0]+","+position[1]);
//		String vis = "";
//		for(Integer[] view: vision.getViewSpan()) {
//			vis+= "|"+view[0]+","+view[1];
//		}
//		System.out.println(this+"vision: "+vis);
		
		//System.out.println("Car "+this+" looking :"+this.look(10*this.length));
	}
	
	/**
	 * Changes the velocity of the car depending on the profil of its driver and whether there shall be acceleration or deceleration
	 */
	public void changeVelocity(boolean accelerate) {
		
		int sign = 1; //Positve or negative (accelerate or decelerate)
		if (!accelerate) {
			sign = -1;

		int distanceIntersection = this.vision.getViewSpanDepth() + 1;

		// Span view limitation if the driver wants to turn
		if (!this.lane.equals(this.destination)) {
			distanceIntersection = (int) Point2D.distance(intersectionPosition[0], this.intersectionPosition[1], this.position[0], this.position[1]);
			if (this.vision.getViewSpanDepth() > distanceIntersection) {
				this.vision.setViewSpanDepth(distanceIntersection);
			}
		}

		this.vision.update();
		Obstacle obstacle = this.vision.look();
		
		if (obstacle.getType().equals(ObstacleType.Empty)) { // No obstacle
			if (this.lane.equals(this.destination)) {
				this.changeVelocity(this.maxVelocity);
			}
			else {
				this.changeVelocity(this.velocityCalculation(20, distanceIntersection)); 
				if (this.velocity > distanceIntersection) this.turn(distanceIntersection);
			}
		}
		else { // Cases : Car, Pedestrian, Red/Orange Traffic Light
				if (canBrake(obstacle.getDistance())) {
					this.changeVelocity(this.velocityCalculation(0, obstacle.getDistance()));
				}
				else {
					int distanceMaxBrake = this.velocity - maxBrake;
					this.velocity -= maxBrake;
				}
			}
		}
	}
	
	/**
	 * changes the position of the car
	 */
	public void go() {
		
		// Changing the position of the car
		OrientedDirection carDirection = this.lane.getOrientedDirection();
		
		boolean test = false;
		int index = -1;
		int expression = 0;
		if (!this.inGarage()) { //Tests if car is in garage position
			switch (carDirection) {
			case NS:
				index = 1;
				expression = position[index] + this.velocity;
				test = (expression <= this.lane.getRoad().getLength());
				break;
			case SN:
				index = 1;
				expression = position[index] - this.velocity;
				test = (expression >= 0);
	    		break;
			case EW:
				index = 0;
				expression = position[index] - this.velocity;
				test = (expression >= 0);
	    		break;
			case WE:
				index = 0;
				expression = position[index] + this.velocity;
				test = (expression <= this.lane.getRoad().getLength());
	    		break;
			}
			if (test) { //test if car is still inside of the simulation after movement
				position[index] = expression; 
				this.visible = true;
			}
			else { //In case it leaves the simulation
				this.park();
			}
		}
	}



	
	/**
	 * changes the position of the car which is aiming to turn
	 */
	public void turn(int distanceTurn) {
		int remainingDistance = this.velocity - distanceTurn;
		
		OrientedDirection carDirection = this.destination.getOrientedDirection();
		
		this.position[0] = this.intersectionPosition[0];
		this.position[1] = this.intersectionPosition[1];
		
		switch (carDirection) {
		case WE: 
			this.position[0] += remainingDistance;
			break;
		case NS: 
			this.position[1] += remainingDistance;
			break;
		case EW: 
			this.position[0] -= remainingDistance;
			break;
		case SN: 
			this.position[1] -= remainingDistance;
			break;
		}
		this.lane = this.destination;
	}

	/**
	 * Changes the velocity of the car depending on the profile of its driver and whether there shall be acceleration or deceleration
	 */
	public void changeVelocity(int requiredVelocity) {
		
		double velocityDifference = requiredVelocity - this.velocity;
		
		this.velocity += Math.signum(velocityDifference) * Math.min(Math.abs(velocityDifference), this.maxChange);
	}

	/**
	 * 
	 * @return 
	 */
	private int velocityCalculation(int requiredVelocity, int distance) {
		int maxSpeed = this.velocity;
		int maxDistanceBrake = 0;
		for (int i = requiredVelocity; i < this.maxVelocity; i += this.maxChange) {
			if (distance < maxDistanceBrake + i) break;
			if (maxSpeed < i) {
				maxSpeed += maxChange;
				break;
			}
			maxDistanceBrake += i;
		}
		return maxSpeed;
	}
	
	
	
	
	/**
	 * Checks if the car is in capacity to brake
	 */
	private boolean canBrake(int distance) {
		int distanceBrake = 0;
		for (int i = this.velocity; i>0; i -= maxChange) {
			distanceBrake += Math.abs(i - maxChange);
		}
		return distance > distanceBrake;
	}

	/*
	 * Max Brake Calculation (according to the French Highway Code)
	 * reactionTime : velocity(km/h) / 10 * 3
	 * brakingTime (dry weather) : (velocity(km/h) / 10)� / 2
	 * NB : conversion km/h to cases is a division by 3 (ie velocity of a pedestrian)
	 * @return (int) necessary distance to brake
	 */
	private int maxBrakeCalculation() {
		int reactionTime = (int) (this.velocity * 3 / 10 * 3) / 3;
		int brakingTime = (int) (Math.pow(this.velocity * 3 / 10, 2) / 2) / 3;
		return reactionTime + brakingTime;
	}
	
	/*
	 * Random selection of a destination between the lanes 
	 * which intersect the origin lane
	 * @return (Lane) the destination lane
	 */
	private Lane destinationRandom() {
		List<Lane> laneCandidates = new ArrayList<Lane>();
		if (this.lane.getRoad().getOrientation().equals(Orientation.Horizontal)) {
			laneCandidates.addAll(movingParts.getSimulation().getStructureParts().getRoad(1).getListLanes());
		}
		else if (this.lane.getRoad().getOrientation().equals(Orientation.Vertical)) {
			laneCandidates.addAll(movingParts.getSimulation().getStructureParts().getRoad(0).getListLanes());
		}
		int nbLanes = laneCandidates.size();
		for (int i = 0; i < nbLanes; i++) {
			laneCandidates.add(this.lane); // Half probability to go straight
		}
		int r = (int) (Math.random()*laneCandidates.size());
		return laneCandidates.get(r);
	}
	
	/*
	 * Position of the intersection between the origin and the destination lanes
	 * @return int[] Center position of the intersection
	 */
	private int[] intersectionCalculation() {
		int[] position = {-1, -1};
		if (!this.lane.equals(this.destination)) {
			if (this.lane.getRoad().getOrientation().equals(Orientation.Horizontal)) {
				position[0] = (this.destination.getRoad().position - this.destination.getCarPositionOnRoad());
				position[1] = (this.lane.getRoad().position + this.lane.getCarPositionOnRoad());
			}
			else if (this.lane.getRoad().getOrientation().equals(Orientation.Vertical)) {
				position[0] = (this.lane.getRoad().position - this.lane.getCarPositionOnRoad());
				position[1] = (this.destination.getRoad().position + this.destination.getCarPositionOnRoad());
			}
		}
		return position;
	}
	
	//Getters
	@Override
	public MobileType getType() {
		return MobileType.Car;
	}
	public int getVelocity() {
		return velocity;
	}
	public int getMaxVelocity() {
		return maxVelocity;
	}
	public Lane getLane() {
		return lane;
	}
	public MovingParts getMovingParts() {
		return movingParts;
	}
	public Vision getVision() {
		return vision;
	}
	

	public double getWaitingTime() {
		return waitingTime;
	}
	
	public double getCrossingDuration() {
		return crossingDuration;
	}
	

	public static void main(String args[]){
		ConfigureStructure structConfig = new ConfigureStructure(120, 120);
		StructureParts structureParts = new StructureParts(structConfig);
		Simulation simulation = new Simulation(structConfig);
		MovingParts movingParts = new MovingParts(simulation, structureParts);
		// Lane lane = structureParts.getRoad(0).getLane(0);
		for (int i=0; i<10; i++) {
			Car car = new Car(movingParts, "voiture", 5, 3, Profil.slow, 10, 17, structureParts.getRoad(0).getLane(0));
//			System.out.println(car.destinationRandom().getOrientedDirection());
			System.out.println("position pour "+car.lane.getOrientedDirection()+"/"+car.destination.getOrientedDirection()+" : "+car.intersectionCalculation()[0]+", "+car.intersectionCalculation()[1]);
			System.out.println("intersection : "+car.intersectionCalculation()[0]+", "+car.intersectionCalculation()[0]);
		}
		Car car2 = new Car(movingParts, "voiture", 5, 3, Profil.respectful, 10, 17, structureParts.getRoad(1).getLane(0));
		System.out.println("position car2 : x="+car2.position[0]+" y="+car2.position[1]+" on lane "+car2.lane.getOrientedDirection());
		
		
		/*
		Car car = new Car(movingParts, "voiture", 5, 3, Profil.slow, 10, 17, structureParts.getRoad(0).getLane(0));
		System.out.println("id : "+car+", model : "+", profile : "+car.profil);
		System.out.println("destination : "+car.destination);
		System.out.println("position car : x="+car.position[0]+" y="+car.position[1]+" on lane "+car.lane.getOrientedDirection());
		car.nextStep();
		System.out.println("position car : x="+car.position[0]+" y="+car.position[1]+" on lane "+car.lane.getOrientedDirection());
		Car car2 = new Car(movingParts, "voiture", 5, 3, Profil.respectful, 10, 17, structureParts.getRoad(0).getLane(0));
		System.out.println("position car2 : x="+car2.position[0]+" y="+car2.position[1]+" on lane "+car2.lane.getOrientedDirection());
		System.out.println("vision car2 : "+car2.vision.look());
		car.nextStep();
		car2.nextStep();
		System.out.println("position car : x="+car.position[0]+" y="+car.position[1]+" on lane "+car.lane.getOrientedDirection());
		System.out.println("position car2 : x="+car2.position[0]+" y="+car2.position[1]+" on lane "+car2.lane.getOrientedDirection());
		System.out.println("vision car2 : "+car2.vision.look());
		*/
	}


}
