package immobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.Orientation;
import enumeration.OrientedDirection;
import immobile.lights.TrafficLightSystem;
import immobile.structures.Lane;
import immobile.structures.Road;
import immobile.structures.SideWalk;
import model.Cell;
import model.ConfigureStructure;

public class StructureParts {
	
	private List<Road> listRoads;
	private TrafficLightSystem trafficLightSystem;
	private Cell[][] structGrid;
	
	public StructureParts(ConfigureStructure structConfig) {
		listRoads = new ArrayList<Road>();		
		listRoads.add(new Road(this, structConfig.columnNb, structConfig.laneSize, structConfig.sideWalkSize, Orientation.Horizontal, 1, structConfig.bidirectional));
		listRoads.add(new Road(this, structConfig.columnNb, structConfig.laneSize, structConfig.sideWalkSize, Orientation.Vertical, 1, structConfig.bidirectional));
		
		this.trafficLightSystem = new TrafficLightSystem(listRoads, 15, 12);
		
		this.structGrid = new Cell[structConfig.lineNb][structConfig.columnNb];
		
		defineCoordinates(structConfig.lineNb, structConfig.columnNb);
		
		initStructGrid();
		
		
	}
	
	
	
	
	/**
	 * Defines the coordinates of the cells
	 */
	public void defineCoordinates(int xLength, int yLength) {
		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				Cell cell = new Cell(x, y);
				structGrid[x][y] = cell;
			}
		}
	}
	
	
	/**
	 * Initializes base grid (only with structures)
	 */
	public void initStructGrid() {
		
		for (Road road : listRoads) {
			if(road.getOrientation() == Orientation.Horizontal) {
				
				int cursor = road.getPosition(); //Our vertical position on the grid throughout this code
				
				//Adding left sidewalk (considering direction defined as true)	
				for(int i=cursor; i<cursor+road.getSideWalkSize(); i++) {
						for (Cell cell: structGrid[i]) {
							cell.addRoad(road);
							cell.addStructure(road.getSideWalk(0));
						}
				}
				cursor += road.getSideWalkSize();
				
				//Adding each lanes				
				for(Lane lane : road.getListLanes()) {	
					for(int i=cursor; i<cursor+road.getLaneSize(); i++) {
							for (Cell cell: structGrid[i]) {
								cell.addRoad(road);
								cell.addStructure(lane);
							}
						}
					cursor += road.getLaneSize();
				}
				
				//Adding right sidewalk (considering direction defined as true)	
				for(int i=cursor; i<cursor+road.getSideWalkSize(); i++) {
						for (Cell cell: structGrid[i]) {
							cell.addRoad(road);
							cell.addStructure(road.getSideWalk(1));
						}
				}
			}
			
			else if(road.getOrientation() == Orientation.Vertical) {
				
				int cursor = road.getPosition()+road.getRoadSize(); //Our vertical position on the grid throughout this code
				
				//Adding left sidewalk (considering direction defined as true)	
				for(int j=cursor; j>cursor-road.getSideWalkSize(); j--) {
						for (int i=0; i<road.getLength(); i++) {
							this.structGrid[i][j].addRoad(road);
							this.structGrid[i][j].addStructure(road.getSideWalk(0));
						}
				}
				cursor -= road.getSideWalkSize();
				
				//Adding each lanes				
				for(Lane lane : road.getListLanes()) {	
					for(int j=cursor; j>cursor-road.getLaneSize(); j--) {
							for (int i=0; i<road.getLength(); i++) {
								this.structGrid[i][j].addRoad(road);
								this.structGrid[i][j].addStructure(lane);
							}
						}
					cursor -= road.getLaneSize();
				}
				
				//Adding right sidewalk (considering direction defined as true)	
				for(int j=cursor; j>cursor-road.getSideWalkSize(); j--) {
						for (int i=0; i<road.getLength(); i++) {
							this.structGrid[i][j].addRoad(road);
							this.structGrid[i][j].addStructure(road.getSideWalk(1));
						}
				}
			}
		}
		
		//==============================================================================================================================================
		//Adding traffic lights for cars
		for (Road road : listRoads) {
			for (Lane lane : road.getListLanes()) {
				OrientedDirection direction = lane.getOrientedDirection();
				int laneSize = road.getLaneSize();
				switch (direction) {
				case NS:
					int x = (int) road.getLength()/2 -						//Here is the center of the map, the center of the crossing section
								laneSize + 1;								//Adapting to the top of the crossing section
					int y = (int) road.getLength()/2 - 						//Middle
								laneSize - road.getSideWalkSize() - 1;		//Adapting (-1 bc before the sidewalk)
					// adding traffic light on the lane before the crossing section
					for (int i = 0; i < laneSize; i++) {
						structGrid[y][x + i].addLight(trafficLightSystem.getListLights().get(1));
						System.out.println("trafic light car added at : " + (int) (y) + "," + (int) (x+i));
					}
					break;
				
				case SN:
					x = (int) road.getLength()/2 + 1;		//Adapting to the top of the crossing section
					y = (int) road.getLength()/2 + 
							laneSize + road.getSideWalkSize();									//Adapting
					// adding traffic light on the lane before the crossing section
					for (int i = 0; i < laneSize; i++) {
						structGrid[y][x + i].addLight(trafficLightSystem.getListLights().get(1));
						System.out.println("trafic light car added at : " + y + "," + (int) (x + i));
					}
					break;
					
					
				case WE:
					x = (int) road.getLength()/2 + 1 -						//Middle
								laneSize - road.getSideWalkSize() - 1;									//Adapting
					y = (int) road.getLength()/2;		//Adapting to the top of the crossing section
					
					// adding traffic light on the lane before the crossing section
					for (int i = 0; i < laneSize; i++) {
						structGrid[y + i][x].addLight(trafficLightSystem.getListLights().get(1));
						System.out.println("trafic light car added at : " + (int) (y + i) + "," + x);
					}
					break;
					
				case EW:
					x = (int) road.getLength()/2 +						//Middle
					laneSize + road.getSideWalkSize() + 1;				//Adapting
					y = (int) road.getLength()/2 -
							laneSize;									//Adapting to the top of the crossing section
					
					// adding traffic light on the lane before the crossing section
					for (int i = 0; i < laneSize; i++) {
						structGrid[y + i][x].addLight(trafficLightSystem.getListLights().get(1));
						System.out.println("trafic light car added at : " + (int) (y + i) + "," + x);
					}
					break;
				
					
				}
				
				
				
			}
			// *** ELIE'S VERSION ***
			/*
			if(road.getOrientation() == Orientation.Vertical) {
				for (Lane lane : road.getListLanes()) { 
					int lightPositionX = road.getPosition() + road.getSideWalkSize() + road.getLaneSize()*(road.getIndexOfLane(lane)) + 1;
					int i = 0;
					if(lane.getDirection() == true) {
						i = listRoads.get(1).getPosition() + listRoads.get(1).getRoadSize() ;
					}
					else if(lane.getDirection() == false) {
						i = listRoads.get(1).getPosition() - 1;
					}
					for (int j=lightPositionX; j < lightPositionX+road.getLaneSize(); j++) {
						structGrid[i][j].addLight(trafficLightSystem.getListLights().get(1));
						System.out.println("trafic light car added at : " + i + "," + j);
					}
				}
			}
			*/
			
			/*
			if(road.getOrientation() == Orientation.Horizontal) {
				for (Lane lane : road.getListLanes()) {
					int lightPositionY = road.getPosition() + road.getSideWalkSize() + road.getLaneSize()*(road.getIndexOfLane(lane));
					int j = 0;
					if(lane.getDirection() == true) {
						j = listRoads.get(0).getPosition();
					}
					else if(lane.getDirection() == false) {
						j = listRoads.get(0).getPosition() + listRoads.get(0).getRoadSize() + 1;
					}
					for (int i=lightPositionY; i < lightPositionY+road.getLaneSize(); i++) {
						structGrid[i][j].addLight(trafficLightSystem.getListLights().get(0));
					}
				}
			}
			*/
			
			
		}
		
		
		
		
		
		//==============================================================================================================================================
		//Adding traffic lights for pedestrian
		
		Road road = this.listRoads.get(1);
		//left-top corner
		int xOrigin = road.getPosition() + 1;
		int yOrigin = listRoads.get(1).getPosition();
		int walkSize = road.getSideWalkSize();
		
		for (int x = 0; x < walkSize; x++) {
			for (int y =0; y < walkSize; y++) {
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(3));
				System.out.println("traffic ped  " + yOrigin + "," + xOrigin);
			}
		}
		
		//right-top corner
		xOrigin = road.getPosition() + walkSize + 2*road.getLaneSize() + 1;
		
		for (int x = 0; x < walkSize; x++) {
			for (int y =0; y < walkSize; y++) {
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(3));
				System.out.println("traffic ped  " + yOrigin + "," + xOrigin);
			}
		}
		
		//left-bottom corner
		xOrigin = road.getPosition() + 1;
		yOrigin = listRoads.get(1).getPosition() + walkSize + 2*road.getLaneSize();
		
		for (int x = 0; x < walkSize; x++) {
			for (int y =0; y < walkSize; y++) {
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(3));
				System.out.println("traffic ped  " + yOrigin + "," + xOrigin);
			}
		}
		
		
		//right-bottom corner
		xOrigin = road.getPosition() + walkSize + 2*road.getLaneSize() + 1;
		yOrigin = listRoads.get(1).getPosition() + walkSize + 2*road.getLaneSize();
		
		for (int x = 0; x < walkSize; x++) {
			for (int y =0; y < walkSize; y++) {
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[yOrigin + y][xOrigin + x].addLight(trafficLightSystem.getListLights().get(3));
				System.out.println("traffic ped  " + yOrigin + "," + xOrigin);
			}
		}
		
		
		
		/*
		int jOrigin = road.getPosition() + road.getSideWalkSize() + road.getLaneSize()*road.getListLanes().size() + 1;
		int iOrigin = listRoads.get(1).getPosition() ;
		System.out.println("traffic pedestrian : " + iOrigin + "," + jOrigin );
		
		for (int j=jOrigin; j < jOrigin+road.getSideWalkSize(); j++) {
			for(int i=iOrigin; i < iOrigin+road.getSideWalkSize(); i++) {
				structGrid[i][j].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[i][j].addLight(trafficLightSystem.getListLights().get(3));
				System.out.println("traffic ped  " + i + "," + j);
			}
		}
		*/
		/*
		//left-top corner
		jOrigin = road.getPosition() - road.getSideWalkSize() + road.getLaneSize() - 1;
		iOrigin = listRoads.get(1).getPosition() ;
		
		for (int j=jOrigin; j < jOrigin+road.getSideWalkSize(); j++) {
			for(int i=iOrigin; i < iOrigin+road.getSideWalkSize(); i++) {
				structGrid[j][i].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[j][i].addLight(trafficLightSystem.getListLights().get(3));			
			}
		}
		
		//right-bottom corner
		jOrigin = road.getPosition() + road.getSideWalkSize() + road.getLaneSize()*road.getListLanes().size() + 1;
		iOrigin = listRoads.get(1).getPosition() + road.getLaneSize()*road.getListLanes().size() + road.getSideWalkSize();
		
		for (int j=jOrigin; j < jOrigin+road.getSideWalkSize(); j++) {
			for(int i=iOrigin; i < iOrigin+road.getSideWalkSize(); i++) {
				structGrid[j][i].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[j][i].addLight(trafficLightSystem.getListLights().get(3));					
			}
		}
		//left-bottom corner
		jOrigin = road.getPosition() - road.getSideWalkSize() + road.getLaneSize() - 1;
		iOrigin = listRoads.get(1).getPosition() + road.getLaneSize()*road.getListLanes().size() + road.getSideWalkSize();
		
		for (int j=jOrigin; j < jOrigin+road.getSideWalkSize(); j++) {
			for(int i=iOrigin; i < iOrigin+road.getSideWalkSize(); i++) {
				structGrid[j][i].addLight(trafficLightSystem.getListLights().get(2));
				structGrid[j][i].addLight(trafficLightSystem.getListLights().get(3));
			}
		}
		*/
	}
	
	
	
	
	
	
	
	
	/**
	 * Getters
	 */
	public Cell[][] getStructGrid() {
		return structGrid;
	}
	public Cell[][] cloneStructGrid(){
		Cell[][] clonedGrid = new Cell[structGrid.length][structGrid[0].length];
		for(int i=0; i<structGrid.length; i++) {
			for(int j=0; j<structGrid[0].length; j++) {
				clonedGrid[i][j] = structGrid[i][j].clone();
			}
		}
		return clonedGrid;
	}
	
	public Cell getCell(int x, int y) {
		return structGrid[x][y];
	}
	public Road getRoad(int i) {
		return listRoads.get(i);
	}
	public TrafficLightSystem getTrafficLightSystem() {
		return trafficLightSystem;
	}
	public List<Road> getListRoads() {
		return listRoads;
	}
	
	
	@Override
	public String toString() {
		String table = "";
		for(int i=0; i<structGrid.length-1; i++) {
			String line = "[";
			for(int j=0; j<structGrid[0].length-1; j++) {
				line += structGrid[i][j]+" ";
			}
			line += "]\n";
			table += line;
		}
		return table;
	}
}

