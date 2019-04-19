package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import immobile.StructureParts;
import mobile.MovingParts;

public class SimulationState {
	/**
	 * This class represents the state of the simulation on the grid
	 */
	private int step;
	private Cell[][] grid;
	
	/**
	 * Construtor
	 * 
	 * @param step
	 * @param lineNb
	 * @param columnNb
	 * @param structureParts
	 */
	public SimulationState(int step, int lineNb, int columnNb, StructureParts structureParts) {
		super();
		this.step = step;
		this.grid = new Cell[lineNb][columnNb];
		defineCoordinates(lineNb, columnNb);
		this.grid = structureParts.getStructGrid();
	}
	
	
	/**
	 * Create cells with coordinates
	 * @param xLength
	 * @param yLength
	 */
	public void defineCoordinates(int xLength, int yLength) {
		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				Cell cell = new Cell(x, y);
				grid[x][y] = cell;
			}
		}
	}
	
	
	/**
	 * DEPRECATED
	 * Generation of every element's position and parameter at this step in the simulation. Filling up every grid's cell.
	 * 
	 * @param previousState
	 * @return true if a termination case presents itself
	 */
	public boolean generate(SimulationState previousState) {
		//Updating grid 
		return true; //true return statement only here for testing
	}
	
	/**
	 * update the position of every moving element on the grid starting from the structGrid model located in "StructureParts"
	 */
	public void updateGrid(MovingParts movingParts) {
		
	}

	@Override
	public String toString() {

		String table = "";
		for(int i=0; i<grid.length-1; i++) {
			String line = "[";
			for(int j=0; j<grid[0].length-1; j++) {
				line += grid[i][j].toString() + " ";
			}
			line += "]\n";
			table += line;
		}
		return table;

	}
	
	
	/**
	 * Write raw content of every cell to file
	 */
	public void writeToFile(String fileName) {
		Path File = Paths.get(fileName);
		
		if (!Files.exists(File)) {
			try {
				Files.createFile(File);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		try (BufferedWriter writer = Files.newBufferedWriter(File, StandardCharsets.UTF_8, StandardOpenOption.WRITE)) { // buffer en ecriture (ecrase l’existant), encodage UTF8
			
					writer.write(this.toString());
					writer.close();
			} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Getters
	 */
	public Cell getGridValue(int i, int j) {
		return grid[i][j];
	}
	public Cell[][] getGrid() {
		return grid;
	}
	
}
