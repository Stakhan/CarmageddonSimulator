package model;

import engine.Simulation;
import enumeration.OrientedDirection;

public class ConfigureFlow {


	private double carFlow;
	private double pedestrianFlow;
	private int redTime;
	private int greenTime;
	private int orangeTime;
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
	public ConfigureFlow(Simulation simulation, double carFlow, double pedestrianFlow, int redTime, int greenTime, int orangeTime) {
		this.simulation = simulation;
		
		this.carFlow = carFlow;
		this.pedestrianFlow = pedestrianFlow;
		this.redTime = redTime;
		this.greenTime = greenTime;
		this.orangeTime = orangeTime;
	}
	
	
	/**
	 * Second constructor, with default value
	 * @param simulation
	 */
	public ConfigureFlow(Simulation simulation) {
		this.simulation = simulation;
		
		this.carFlow = 0.01;
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
		for (int i = 0; i < 8; i++) {
			if (Math.random() < carFlow) {
				this.simulation.getMovingParts().addRandomCar();
			}
		}
	}
	
	public void configureFlowCar(OrientedDirection direction) {
		for (int i = 0; i < 8; i++) {
			if (Math.random() < carFlow) {
				this.simulation.getMovingParts().addRandomPedestrianDirection(direction);
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

	public int getRedTime() {
		return redTime;
	}

	public void setRedTime(int redTime) {
		this.redTime = redTime;
	}

	public int getGreenTime() {
		return greenTime;
	}

	public void setGreenTime(int greenTime) {
		this.greenTime = greenTime;
	}

	public int getOrangeTime() {
		return orangeTime;
	}

	public void setOrangeTime(int orangeTime) {
		this.orangeTime = orangeTime;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	
	
	
}
