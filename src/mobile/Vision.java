package mobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.Color;
import enumeration.MobileType;
import enumeration.ObstacleType;
import enumeration.OrientedDirection;
import model.Cell;
import model.SimulationState;

public class Vision {
	
	private int viewSpanDepth;
	private List<Integer[]> viewSpan;
	private Car car;
	
	public Vision(int viewSpanDepth, Car car) {
			this.viewSpanDepth = viewSpanDepth;
			this.viewSpan = new ArrayList<Integer[]>();
			this.car = car;
		}

	public Vision(Car car) {
		this.viewSpanDepth = Math.round(20 + car.getVelocity() / car.getMaxVelocity() * 40); // visibility depends on velocity, between 20 and 35
		this.viewSpan = new ArrayList<Integer[]>();
		this.car = car;
	}
	
	/**
	 * Update the cells coordinates of the view span of the MobileObject
	 */
	public void update(){
			OrientedDirection carDirection = this.car.getLane().getOrientedDirection();
			int[] position = this.car.getPosition();
			int length = this.car.getLength();

			this.viewSpan.clear(); //Clear previous viewSpan
			
			int i = position[1]-1;
			int j = position[0]-1;
			int maxSight;

			switch (carDirection) {
				case WE:
					int columnNb = this.car.getMovingParts().getSimulation().getColumnNb();
					maxSight = Math.min(position[0]-1 + viewSpanDepth, columnNb - position[0]);
					for (j = position[0] + Math.round(length/2); j < maxSight; j++) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
					break;
				case EW:
					maxSight = Math.max(position[0]-1 - viewSpanDepth, 0);
					for (j = position[0] - 2 - Math.round(length/2); j > position[0]-1 - viewSpanDepth; j--) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
					break;
				case NS:
					maxSight = Math.max(position[1]-1 + viewSpanDepth, 0);
					for (i = position[1] + Math.round(length/2); i < position[1]-1 + viewSpanDepth; i++) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
					break;
				case SN:
					int nbLine = this.car.getMovingParts().getSimulation().getLineNb();
					maxSight = Math.min(position[1]-1 + viewSpanDepth, nbLine - position[1]);
					for (i = position[1] - 2 - Math.round(length/2); i > maxSight; i--) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
			}
//			System.out.println("inverting list");
//			Collections.reverse(this.viewSpan);				
		}
	
	public Obstacle look() {
		
		Obstacle obstacle = new Obstacle(0, ObstacleType.Empty);
		
		if(!this.car.inGarage()) {
			Cell[][] grid = null;
			
			if (this.car.getMovingParts().getSimulation().getListStates().size() > 1) { //We need two states to get a previous state
				//Fetching previous state
				SimulationState previousState = this.car.getMovingParts().getSimulation().getLastState();
				//Fetching grid of previous step
				grid = previousState.getGrid();
			}
			else { //In case it is the first state
				grid = this.car.getMovingParts().getSimulation().getStructureParts().getStructGrid();
			}
			
			int distance = 1;
			for (Integer[] coord : this.viewSpan) {
				int i = coord[0];
				int j = coord[1];
				if (grid[i][j].getContainedMobileObjects().size() != 0) {
					if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Car) {
						obstacle = new Obstacle(distance, ObstacleType.Car);
						break;
					}
					else if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Pedestrian) {
						obstacle = new Obstacle(distance, ObstacleType.Pedestrian);
						break;
					}
				}
				else if (grid[i][j].getContainedLights().size() != 0) {
					if (!grid[i][j].getContainedLights().get(0).getCurrentColor().equals(Color.Green)) { //In case it is Yellow or Red
						obstacle = new Obstacle(distance, ObstacleType.TrafficLight);
						break;
					}
					else {
						obstacle = new Obstacle(0, ObstacleType.Empty);
						break;
					}
				}
				
				distance++;
			}
		}
		else {
			obstacle = new Obstacle(0, ObstacleType.Empty); //Empty obstacle
		}
		
		return obstacle;
		
	}

		/**
		 * @deprecated
		 * Look for specific obstacle.
		 * Was being used for testing.
		 * @param obType to look for
		 * @return the obstacle if it sees it or an empty obstacle
		 */
		public Obstacle look(ObstacleType obType) {
			
			Obstacle obstacle = new Obstacle(0, ObstacleType.Empty);
			
			if(!this.car.inGarage()) {
				Cell[][] grid = null;
				
				
				if (this.car.getMovingParts().getSimulation().getListStates().size() > 1) { //We need two states to get a previous state
					//Fetching previous state
					SimulationState previousState = this.car.getMovingParts().getSimulation().getLastState();
					//Fetching grid of previous step
					grid = previousState.getGrid();
				}
				else { //In case it is the first state
					grid = this.car.getMovingParts().getSimulation().getStructureParts().getStructGrid();

				}
				
				int distance = 1;
				for (Integer[] coord : this.viewSpan) {
					int i = coord[0];
					int j = coord[1];
					if ((obType == ObstacleType.Car || obType == ObstacleType.Pedestrian) && grid[i][j].getContainedMobileObjects().size() != 0) {
						if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Car) {
							obstacle = new Obstacle(distance, ObstacleType.Car);
						}
						else if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Pedestrian) {
							obstacle = new Obstacle(distance, ObstacleType.Pedestrian);
						}
					}
					else if (obType == ObstacleType.TrafficLight && grid[i][j].getContainedLights().size() != 0) {
						if (!grid[i][j].getContainedLights().get(0).getCurrentColor().equals(Color.Green)) { //In case it is Yellow or Red
							obstacle = new Obstacle(distance, ObstacleType.TrafficLight);
							break;
						}
						else {
							obstacle = new Obstacle(0, ObstacleType.Empty);
						}
					}
					distance++;
				}
				
			}
			else {
				obstacle = new Obstacle();
			}

		return obstacle;
	}
	
	//Getters	
	public List<Integer[]> getViewSpan() {
		return viewSpan;
	}
	public int getViewSpanDepth() {
		return viewSpanDepth;
	}
	//Setters	
	public void setViewSpanDepth(int viewSpanDepth) {
		this.viewSpanDepth = viewSpanDepth;
	}
}
