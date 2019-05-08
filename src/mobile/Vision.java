package mobile;

import java.util.ArrayList;
import java.util.List;

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
			switch (carDirection) {
				case WE:
					for (j = position[0]+((int) length/2); j < position[0]-1 + viewSpanDepth; j++) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
					break;
				case EW:
					for (j = position[0]-2-((int) length/2); j > position[0]-1 - viewSpanDepth; j--) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
					break;
				case NS:
					for (i = position[1]+((int) length/2); i < position[1]-1 + viewSpanDepth; i++) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
					break;
				case SN:
					for (i = position[1]-2-((int) length/2); i > position[1]-1 - viewSpanDepth; i--) {
						Integer[] couple = {i,j};
						this.viewSpan.add(couple);
					}
			}
					
		}
	
	public Obstacle look() {
		
		OrientedDirection carDirection = this.car.getLane().getOrientedDirection();
		Cell[][] grid = null;
		
		if (this.car.getMovingParts().getSimulation().getListStates().size() > 1) { //We need two states to get a previous state
			//Fetching previous state
			SimulationState previousState = this.car.getMovingParts().getSimulation().getState(this.car.getMovingParts().getSimulation().getLastState().getStep()-1);
			//Fetching grid of previous step
			grid = previousState.getGrid();
		}
		else { //In case it is the first state
			grid = this.car.getMovingParts().getSimulation().getStructureParts().getStructGrid();
		}
		
		int distance = 0;
		
		for (Integer[] coord : this.viewSpan) {
			int i = coord[0];
			int j = coord[1];
			if (grid[i][j].getContainedMobileObjects().size() != 0) {
				if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Car) {
					return new Obstacle(distance, ObstacleType.Car);
				}
				else if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Pedestrian) {
					return new Obstacle(distance, ObstacleType.Pedestrian);
				}
			}
			else if (grid[i][j].getContainedLights().size() != 0) {
				return new Obstacle(distance, ObstacleType.TrafficLight);
			}
			distance++;
		}
		//In case nothing is in the viewSpan, returning null object
		return new Obstacle();
	}
	
	//Getters
	
	public List<Integer[]> getViewSpan() {
		return viewSpan;
	}
}
