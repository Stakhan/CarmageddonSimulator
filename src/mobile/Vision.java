package mobile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import enumeration.TrafficColor;
import enumeration.MobileType;
import enumeration.ObstacleType;
import enumeration.Orientation;
import enumeration.OrientedDirection;
import model.Cell;
import model.SimulationState;

public class Vision {
	
	private int viewSpanDepth;
	private Car car;
	
	
	/**
	 * Constructor of a vision (from a defined car)
	 * @param viewSpanDepth : the length of the vision
	 * @param car
	 */
	public Vision(int viewSpanDepth, Car car) {
			this.viewSpanDepth = viewSpanDepth;
			this.car = car;
		}


	/**
	 * This methods is used to recalculate a view. In fact, the view decreases when a car is near a simulation's border.
	 * Useful to avoid index out of range when the view is used to add cells etc.
	 * @param view
	 */
	public void updateView(int view) {
		int[] position = car.getPosition();
		OrientedDirection direction = car.getLane().getOrientedDirection();
		
		int newView = view;
		
		switch (direction) {
		case NS:
			if (position[0] + view + (int) car.getLength()/2 + 1 > car.getLane().getRoad().getLength()) {
				newView = car.getLane().getRoad().getLength() - position[0]  - (int) car.getLength()/2 - 1;
			}
			break;
		case SN:
			if (position[0] - view - (int) car.getLength()/2 - 1 < 0) {
				newView = position[0] - (int) car.getLength()/2;
			}
			break;
		case WE:
			if (position[1] + view + (int) car.getLength()/2 + 1> car.getLane().getRoad().getLength()) {
				newView = car.getLane().getRoad().getLength() - position[1] - (int) car.getLength()/2 - 1;
			}
			break;
		case EW:
			if (position[1] - view - (int) car.getLength()/2 - 1 < 0) {
				newView = position[1] - (int) car.getLength()/2;
			}
			break;
		}
		
		if (newView < 0) {
			newView = 0;
		}
		//System.out.println("view Length : " + newView);
		this.viewSpanDepth = newView;
	}
	
	
	
	/**
	 * This methods is used to know the position of the cells on the visionView
	 * @return a list of all the cells IN FRONT of the car
	 */
	public List<Integer[]> getViewList(){
		List<Integer[]> viewList = new ArrayList<>();
		if (!car.inGarage()) {

			Cell[][] grid = this.car.getMovingParts().getSimulation().getStructureParts().getStructGrid();
			
			int[] position = car.getPosition();
			OrientedDirection direction = car.getLane().getOrientedDirection();
			
			updateView(this.viewSpanDepth);
			
			switch (direction) {
			case NS:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0] + i + (int) car.getLength()/2 + 1, position[1]};
					viewList.add(coord);
				}
				break;
			case SN:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0] - i - (int) car.getLength()/2 - 1, position[1]};
					viewList.add(coord);
				}
				break;
			case WE:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0], position[1]  + i + (int) car.getLength()/2 + 1};
					//System.out.println("coord : " + coord[0] +"," + coord[1]);
					viewList.add(coord);
				}
				break;
			case EW:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0], position[1] - i - (int) car.getLength()/2 - 1};
					viewList.add(coord);
				}
				break;	
			}	
		}
		return viewList;	
	}
	
		
	public Obstacle look() {
		
		Integer[] coordObstacle = {-1, -1};
		Obstacle obstacle = new Obstacle(coordObstacle, ObstacleType.Empty);
		
		if(!this.car.inGarage()) {
			
			//System.out.println("test");
			
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

			
			updateView(this.viewSpanDepth);
			
			//System.out.println(toString());
			int distance = 0;
			for (Integer[] coord : getViewList()) {
				int i = coord[0];
				int j = coord[1];
				if (grid[i][j].getContainedMobileObjects().size() != 0) {
					if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Car) {
						coordObstacle[0] = i;
						coordObstacle[1] = j;
						obstacle = new Obstacle(coordObstacle, grid[i][j].getContainedMobileObjects(0), ObstacleType.Car, distance);
						//System.out.println("test 2 : CAR ");
						break;
					}
					else if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Pedestrian) {
						coordObstacle[0] = i;
						coordObstacle[1] = j;

						//System.out.println("test 2 : PEDESTRIAN");

						obstacle = new Obstacle(coordObstacle, grid[i][j].getContainedMobileObjects(0),ObstacleType.Pedestrian, distance);
						break;
					}
				}

				else if (grid[i][j].getContainedLights().size() != 0) {
					if (!grid[i][j].getContainedLights().get(0).getCurrentColor().equals(TrafficColor.Green)) { //In case it is Yellow or Red
						coordObstacle[0] = i;
						coordObstacle[1] = j;
						obstacle = new Obstacle(coordObstacle, grid[i][j].getContainedLights().get(0), ObstacleType.TrafficLight, distance);
						//System.out.println("test 2 : TRAFFIC LIGHT");
						break;
					}
					else {
						//System.out.println("test 2 : EMPTY ");
						obstacle = new Obstacle(coordObstacle, ObstacleType.Empty);
						break;
					}

				}
				distance++;
			}
			
		}
		else {

			//System.out.println("test EMPTY 2");
			obstacle = new Obstacle(coordObstacle, ObstacleType.Empty); //Empty obstacle
		}

		return obstacle;
		
	}

	

	// GETTERS
	
	public int getViewSpanDepth() {
		return viewSpanDepth;
	}
	
	
	// SETTERS
	
	public void setViewSpanDepth(int viewSpanDepth) {
		this.viewSpanDepth = viewSpanDepth;
	}
	
	@Override
	public String toString() {
		String view = "viewList : ";
		for (Integer[] coord : getViewList()) {
			view += "[" + coord[0] + "," + coord[1] + "]" + " , ";
		}
		return view;
	}
	
}
