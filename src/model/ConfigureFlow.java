package model;

import engine.Simulation;
import enumeration.OrientedDirection;

public class ConfigureFlow {


	private double carFlow;
	private double pedestrianFlow;
	private Simulation simulation;
	
	/** 
	 * Detailed constructor
	 * @param simulation
	 * @param carFlow
	 * @param pedestrianFlow
	 * @param redTime
	 * @param greenTime
	 * @param orangeTime
	 */
	@Deprecated
	public ConfigureFlow(Simulation simulation, double carFlow, double pedestrianFlow) {
		this.simulation = simulation;
		
		this.carFlow = carFlow;
		this.pedestrianFlow = pedestrianFlow;
	}
	
	
	/**
	 * Main constructor, with default value.
	 * @param simulation
	 */
	public ConfigureFlow(Simulation simulation) {
		this.simulation = simulation;
		
		this.carFlow = 0.07;
		this.pedestrianFlow = 0.1;
	}
	
	
	
	/**
	 * Pedestrian flow. Max is 8 pedestrian generated on a state.
	 * 8 for the 8 sidewalk.
	 * More will crash the display.
	 * @param pedestrianFlow
	 */
	public void configureFlowPedestrian() {
		for (int i = 0; i < 8; i++) {
			if (Math.random() < pedestrianFlow) {
				this.simulation.getMovingParts().addRandomPedestrian();
			}
		}
	}
	
	public void configureFlowPedestrian(OrientedDirection direction) {
		for (int i = 0; i < 8; i++) {
			if (Math.random() < pedestrianFlow) {
				this.simulation.getMovingParts().addRandomPedestrianDirection(direction);
			}
		}
	}
	public void configureFlowCar() {
		for (int i = 0; i < 2; i++) {
			if (Math.random() < carFlow) {
				this.simulation.getMovingParts().addRandomCar();
			}
		}
	}
	
	public void configureFlowCar(OrientedDirection direction) {
		for (int i = 0; i < 2; i++) {
			if (Math.random() < carFlow) {
				this.simulation.getMovingParts().addRandomCarDirection(direction);
			}
		}
	}
	
	//=============================================================================================================================================
	// GETTERS / SETTERS
	public double getCarFlow() {
		return carFlow;
	}

	public void setCarFlow(double carFlow) {
		this.carFlow = carFlow;
	}

	public double getPedestrianFlow() {
		return pedestrianFlow;
	}

	public void setPedestrianFlow(double pedestrianFlow) {
		this.pedestrianFlow = pedestrianFlow;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	
	
	
}
