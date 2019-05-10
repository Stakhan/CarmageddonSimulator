package mobile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import enumeration.Color;
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
	 * This methods is used to recalculate a view. In fact, the view decrease when a car is near a simulation's border.
	 * Useful to avoid index out of range when the view is used to add cells etc.
	 * @param view
	 */
	public void updateView(int view) {
		int[] position = car.getPosition();
		OrientedDirection direction = car.getLane().getOrientedDirection();
		
		int newView = view;
		
		switch (direction) {
		case NS:
			if (position[1] + view + (int) car.getLength()/2 + 1> car.getLane().getRoad().getLength()) {
				newView = car.getLane().getRoad().getLength() - position[1]  - (int) car.getLength()/2;
			}
			break;
		case SN:
			if (position[1] - view - (int) car.getLength()/2 - 1 < 0) {
				newView = position[1] - (int) car.getLength()/2 - 1;
			}
			break;
		case WE:
			if (position[0] + view + (int) car.getLength()/2 + 1> car.getLane().getRoad().getLength()) {
				newView = car.getLane().getRoad().getLength() - position[0] - (int) car.getLength()/2;
			}
			break;
		case EW:
			if (position[0] - view - (int) car.getLength()/2 - 1 < 0) {
				newView = position[0] - (int) car.getLength()/2 - 1;
			}
			break;
		}
		
		if (newView < 0) {
			newView = 0;
		}
		
		this.viewSpanDepth = newView;
	}
	
	
	
	/**
	 * This methods is used to know the position of the cells on the visionView
	 * @return a list of all the cells IN FRONT of the car
	 */
	public List<Integer[]> getViewList(){
		List<Integer[]> viewList = new ArrayList<>();
		if (!car.inGarage()) {
			
			int[] position = car.getPosition();
			OrientedDirection direction = car.getLane().getOrientedDirection();
			
			switch (direction) {
			case NS:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0], position[1] + i + (int) car.getLength()/2 + 1};
					viewList.add(coord);
				}
				break;
			case SN:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0], position[1] - i - (int) car.getLength()/2 - 1};
					viewList.add(coord);
				}
				break;
			case WE:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0] + i + (int) car.getLength()/2 + 1, position[1]};
					//System.out.println("coord : " + coord[0] +"," + coord[1]);
					viewList.add(coord);
				}
				break;
			case EW:
				for (int i = 0; i < viewSpanDepth; i++) {
					Integer[] coord = {position[0] - i - (int) car.getLength()/2 - 1, position[1]};
					viewList.add(coord);
				}
				break;	
			}	
		}
		return viewList;	
	}
	
	
	/**
	 * 
	 * @return
	 */
	/*
	public void look() {
		Cell[][] grid = this.car.getMovingParts().getSimulation().getStructureParts().getStructGrid();
		
		List<Integer[]> viewList = getViewList();
		for (Integer[] coord : viewList) {

			System.out.println("coord : " + coord[0] + "," + coord[1]);
			if (grid[coord[0]][coord[1]].getContainedMobileObjects().size() != 0) {
				
				System.out.println("test");
				
				MobileType mobileType = grid[coord[0]][coord[1]].getContainedMobileObjects().get(0).getType();
				switch (mobileType) {
				case Pedestrian:
					System.out.println("PIETONS !!!!!!");
					break;
				case Car:
					System.out.println("VOITURE !!!!!!");
					break;
				}
			
			}
			// Test if the case contains a redLight or OrangeLight : it is an obstacle
			
			if (grid[coord[0]][coord[1]].getContainedLights().get(0).getCurrentColor() == Color.Red) {
				System.out.println("FEUX ROUGE !!!!!!");
				break;
				
			}
			
		}
	}
	*/

	
	public Obstacle look() {
		
		Integer[] coordObstacle = {-1, -1};
		Obstacle obstacle = new Obstacle(coordObstacle, ObstacleType.Empty);
		
		if(!this.car.inGarage()) {
			Cell[][] grid = null;
			
			grid = this.car.getMovingParts().getSimulation().getStructureParts().getStructGrid();
			
			updateView(this.viewSpanDepth);
			
			System.out.println(toString());
			for (Integer[] coord : getViewList()) {
				int i = coord[0] - 1;
				int j = coord[1] - 1;

				if (grid[i][j].getContainedMobileObjects().size() != 0) {
					System.out.println("prout");
					if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Car) {
						coordObstacle[0] = i;
						coordObstacle[1] = j;
						obstacle = new Obstacle(coordObstacle, ObstacleType.Car);
						System.out.println("test 2 : CAR ");
						break;
					}
					else if (grid[i][j].getContainedMobileObjects(0).getType() == MobileType.Pedestrian) {
						coordObstacle[0] = i;
						coordObstacle[1] = j;
						System.out.println("test 2 : PEDESTRIAN");
						obstacle = new Obstacle(coordObstacle, ObstacleType.Pedestrian);
						break;
					}
				}
				if (grid[i][j].getContainedLights().size() != 0) {
						coordObstacle[0] = i;
						coordObstacle[1] = j;
						System.out.println("test 2 : TRAFFIC LIGHT");
						obstacle = new Obstacle(coordObstacle, ObstacleType.TrafficLight);
						break;
				}
			}
		}
		else {
			System.out.println("test EMPTY 2");
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
