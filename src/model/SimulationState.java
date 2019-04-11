package model;

import immobile.StructureParts;

public class SimulationState {
	
	private int step;
	private Cell[][] grid;
	private StructureParts structureParts;
	
	//Constructor
	public SimulationState(int step, int lineNb, int columnNb) {
		super();
		this.step = step;
		this.grid = new Cell[lineNb][columnNb];
	}
	
	
	/**
	 * Generation of every element's position and parameter at this step in the simulation. Filling up every grid's cell.
	 * 
	 * @param previousState
	 * @return true if a termination case presents itself
	 */
	public boolean generate(SimulationState previousState) {
		//Fixing every structure part

		for(Cell[] cell: line) {
			cell.
		}
		return true; //true return statement only here for testing
	}
	
	@Override
	public String toString() {
		String table = "";
		for(int i=0; i<grid.length-1; i++) {
			String line = "[";
			for(int j=0; j<grid[0].length-1; j++) {
				line += grid[i][j]+" ";
			}
			line += "]\n";
			table += line;
		}
		return table;
	}
	
	
}
