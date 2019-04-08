package model.base;

import model.structure.StructureParts;

public class SimulationState {
	
	private int step;
	private Cell[][] grid;
	private StructureParts structureParts;
	
	//Constructeur
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
		//Update every simulation element
//		for(Cell[] line: this.grid) {
//			for(Cell cell: line) {
//				cell = null;
//			}
//		}
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
