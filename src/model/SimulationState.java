package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import engine.Simulation;
import enumeration.OrientedDirection;
import immobile.lights.TrafficLightSystem;
import mobile.Car;
import mobile.Pedestrian;
import stats.Statistics;

public class SimulationState {
	/**
	 * This class represents the state of the simulation on the grid
	 */
	Simulation simulation;
	private int step;
	private Cell[][] grid;
	private TrafficLightSystem trafficLightSystem;
	private Statistics stats;
	
	/**
	 * Constructor
	 * 
	 * @param step
	 * @param lineNb
	 * @param columnNb
	 * @param structureParts
	 */
	public SimulationState(Simulation simulation, int step) {
		super();
		this.simulation = simulation;
		this.step = step;
		this.grid = this.simulation.getStructureParts().cloneStructGrid();
		this.trafficLightSystem = this.simulation.getStructureParts().getTrafficLightSystem().clone();
		
		// Adding the statistics in the attribute stats
		computeStats();
	}
	
	
	public void computeStats() {
		// Computing the statistics
		Statistics stats = new Statistics(this);
		// PEDESTRIANS
		// * CrossingDuration *
		List<Double> listPedestriansCD = new ArrayList<>();
		List<Pedestrian> listPedestrians = simulation.getMovingParts().getListPedestrians();
		for (int i = 0; i < listPedestrians.size(); i++) {
			listPedestriansCD.add(new Double(listPedestrians.get(i).getCrossingDuration())); // cast in Double
		}
		// * WaitingTime * 
		List<Double> listPedestriansWT = new ArrayList<>();
		for (int i = 0; i < listPedestrians.size(); i++) {
			listPedestriansWT.add(new Double(listPedestrians.get(i).getWaitingTime())); // cast in Double
		}
		
		// CARS
		// * CrossingDuration *
		List<Double> listCarsCD = new ArrayList<>();
		List<Car> listCars = simulation.getMovingParts().getListCars();
		for (int i = 0; i < listCars.size(); i++) {
			listCarsCD.add(new Double(listCars.get(i).getCrossingDuration())); // cast in Double
		}
		// * WaitingTime * 
		List<Double> listCarsWT = new ArrayList<>();
		for (int i = 0; i < listCars.size(); i++) {
			listCarsWT.add(new Double(listCars.get(i).getWaitingTime())); // cast in Double
		}
		Double averageCrossingDurationPedestrian = stats.average(listPedestriansCD);
		Double averageWaitingTimePedestrian = stats.average(listPedestriansWT);
		Double averageCrossingDurationCar = stats.average(listCarsCD);
		Double averageWaitingTimeCar = stats.average(listCarsWT);
		
		// Setting the stats
		stats.setAverageCrossingDurationPedestrian(averageCrossingDurationPedestrian);
		stats.setAverageWaitingTimePedestrian(averageWaitingTimePedestrian);
		stats.setAverageCrossingDurationCar(averageCrossingDurationCar);
		stats.setAverageWaitingTimeCar(averageWaitingTimeCar);
		
		//System.out.println(stats.toString());
		
		// Updating the attribute stats
		this.stats = stats;
		
	}
	
	


	/**
	 * @deprecated
	 * Gives next simulation state from previous one
	 * @return SimulationState
	 */
	public  SimulationState nextState() {
		SimulationState next = new SimulationState(this.simulation, this.step+1);
		for(Car car : simulation.getMovingParts().getListCars()) {
			if (!car.inGarage()) { //Make sure car is in simulation
				car.nextStep();
				car.draw(next.getGrid());
			}
		}
		
		for(Pedestrian pedestrian : simulation.getMovingParts().getListPedestrians()) {
			if (!pedestrian.inGarage()) { //Make sure pedestrian is in simulation
				pedestrian.nextStep();
				pedestrian.draw(next.getGrid());
			}
		}
		
		// Adding the flow
		
		
		//simulation.getConfiguredFlow().configureFlowCar();
		//simulation.getConfiguredFlow().configureFlowPedestrian();
		
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
	
	public TrafficLightSystem getTrafficLightSystem() {
		return trafficLightSystem;
	}
	
	public Statistics getStatistics() {
		return stats;
	}
	
}
