
package engine;

import java.util.ArrayList;
import java.util.List;

import enumeration.OrientedDirection;
import immobile.StructureParts;
import mobile.Car;
import mobile.MovingParts;
import mobile.Pedestrian;
import model.ConfigureFlow;
import model.ConfigureStructure;
import model.SimulationState;

public class Simulation {
	
	private List<SimulationState> listStates;
	private int lineNb;
	private int columnNb;
	private StructureParts structureParts;
	private MovingParts movingParts;
	private ConfigureFlow configuredFlow;
	
	
	//Constructeur
	public Simulation(ConfigureStructure structConfig) {
		
		this.listStates = new ArrayList<SimulationState>();
		
		this.lineNb = structConfig.lineNb;
		this.columnNb = structConfig.columnNb;
		
		this.structureParts = new StructureParts(structConfig);
		this.movingParts = new MovingParts(this, this.structureParts);
	}
	

	/**
	 * Initialization of the first state
	 */
	public void init() {
		this.movingParts.generate(); //Adding MobileObjects to the simulation
		
		SimulationState initState = new SimulationState(this, 0);
		
		for(Car car : this.getMovingParts().getListCars()) {
			car.nextStep();
			car.draw(initState.getGrid());
		}

		//updating pedestrians
		for(Pedestrian ped : this.getMovingParts().getListPedestrians()) {
			if (!ped.inGarage()) { //Make sure car is in simulation
				//ped.nextStep();
				ped.draw(initState.getGrid());
			}
		}
		//updating traffic light system
		this.structureParts.getTrafficLightSystem().nextStep(initState.getStep());
		listStates.add(initState);
		
	}
	
	public void nextState() {
		//Constructing next state
		SimulationState next = new SimulationState(this, this.getLastState().getStep()+1);
		
		// *** Updating moving object with flow ***
		this.getConfiguredFlow().configureFlowCar();
		this.getConfiguredFlow().configureFlowPedestrian();
		
		//updating cars
		for(Car car : this.getMovingParts().getListCars()) {
			if (!car.inGarage()) { //Make sure car is in simulation
				car.nextStep();
				car.draw(next.getGrid());
			}
		}
		
		//updating pedestrians
		for(Pedestrian ped : this.getMovingParts().getListPedestrians()) {
			if (!ped.inGarage()) { //Make sure car is in simulation
				ped.nextStep();
				ped.draw(next.getGrid());
			}
		}

		//updating traffic light system
		this.structureParts.getTrafficLightSystem().nextStep(next.getStep());

		 
		listStates.add(next); //compute next state and add it to the list of states
	}
	

	
	/**
	 * Refresh the list of simulation, and delete all previous simulationState except the n last ones.
	 * @param n
	 */
	public void clearSimulationState(int n) {
		if( n < this.listStates.size()) {
			for (int k = 0; k < this.listStates.size() - n; k++){
				this.listStates.remove(k);
			}
		}
	}
	
	
	
	//Getters
	
	public StructureParts getStructureParts() {
		return structureParts;
	}
	
	public MovingParts getMovingParts() {
		return movingParts;
	}
	
	public int getLineNb() {
		return lineNb;
	}
	
	public int getColumnNb() {
		return columnNb;
	}
	
	public SimulationState getLastState() {
		return this.listStates.get(listStates.size()-1);
	}

	public List<SimulationState> getListStates() {
		return listStates;
	}
	
	/**
	 * Get a state of the simulation at the given index
	 * @param index
	 * @return SimulationState object
	 */
	public SimulationState getState(int index) {
		return this.listStates.get(index);
	}
	
	public ConfigureFlow getConfiguredFlow() {
		return configuredFlow;
	}
	
	
	// SETTERS
	public void setConfiguredFlow(ConfigureFlow configuredFlow) {
		this.configuredFlow = configuredFlow;
	}
	
}
