package immobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.Orientation;
import immobile.lights.TrafficLightSystem;
import immobile.structures.Lane;
import immobile.structures.Road;
import model.Cell;
import model.ConfigureStructure;

public class StructureParts {
	
	private List<Road> listRoads;
	private TrafficLightSystem trafficLightSystem;
	private Cell[][] structGrid;
	
	public StructureParts(ConfigureStructure structConfig) {
		listRoads = new ArrayList<Road>();
		
		listRoads.add(new Road(structConfig.columnNb, structConfig.laneSize, structConfig.sideWalkSize, Orientation.Horizontal, 1, structConfig.bidirectional));
		listRoads.add(new Road(structConfig.columnNb, structConfig.laneSize, structConfig.sideWalkSize, Orientation.Vertical, 1, structConfig.bidirectional));
		
		this.structGrid = new Cell[structConfig.lineNb][structConfig.columnNb];
		
		defineCoordinates(structConfig.lineNb, structConfig.columnNb);
		
		initStructGrid();
	}
	
	
	/***
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
					for(int j=cursor; j<cursor+road.getLaneSize(); j++) {
							for (Cell cell: structGrid[j]) {
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
				for(int i=cursor; i<cursor-road.getSideWalkSize(); i--) {
						for (Cell cell: structGrid[i]) {
							cell.addRoad(road);
							cell.addStructure(road.getSideWalk(0));
						}
				}
				cursor -= road.getSideWalkSize();
				
				//Adding each lanes				
				for(Lane lane : road.getListLanes()) {	
					for(int j=cursor; j<cursor-road.getLaneSize(); j--) {
							for (Cell cell: structGrid[j]) {
								cell.addRoad(road);
								cell.addStructure(lane);
							}
						}
					cursor -= road.getLaneSize();
				}
				
				//Adding right sidewalk (considering direction defined as true)	
				for(int i=cursor; i<cursor-road.getSideWalkSize(); i--) {
						for (Cell cell: structGrid[i]) {
							cell.addRoad(road);
							cell.addStructure(road.getSideWalk(1));
						}
				}
				
			}
		}
	}
	
	/**
	 * Getters
	 */
	public Cell[][] getStructGrid() {
		return structGrid;
	}
	
	public Cell getCell(int x, int y) {
		return structGrid[x][y];
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
