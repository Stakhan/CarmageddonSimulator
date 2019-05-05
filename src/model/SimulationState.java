package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import engine.Simulation;
import mobile.Car;

public class SimulationState {
	/**
	 * This class represents the state of the simulation on the grid
	 */
	Simulation simulation;
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
	public SimulationState(Simulation simulation, int step, int lineNb, int columnNb) {
		super();
		this.simulation = simulation;
		this.step = step;
		//this.grid = new Cell[lineNb][columnNb];
		this.grid = this.simulation.getStructureParts().cloneStructGrid();
	}
	
	
	
	/**
	 * Gives next simulation state from previous one
	 * @return SimulationState
	 */
	public  SimulationState nextState() {
		SimulationState next = new SimulationState(this.simulation, this.step+1, this.simulation.getLineNb(), this.simulation.getColumnNb());
			for(Car car : simulation.getMovingParts().getListCars()) {
				car.nextStep();
				car.draw(next.getGrid());
		}
		
		
		//DEV-NOTE: Should test for collision somehow (next.setStep(-1) could signal termination)
		
		return next;
	}
	

//	@Override
//	public String toString() {
//
//		String table = "";
//		for(int i=0; i<grid.length-1; i++) {
//			String line = "[";
//			for(int j=0; j<grid[0].length-1; j++) {
//				line += grid[i][j].toString() + " ";
//			}
//			line += "]\n";
//			table += line;
//		}
//		return table;
//
//	}
	
	
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


	//Getters
	 
	public Cell getGridValue(int i, int j) {
		return grid[i][j];
	}
	
	public Cell[][] getGrid() {
		return grid;
	}
	
	public int getStep() {
		return step;
	}
}
