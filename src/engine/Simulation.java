package engine;

import java.util.ArrayList;
import java.util.List;

import immobile.StructureParts;
import mobile.MovingParts;
import model.ConfigureStructure;
import model.SimulationState;

public class Simulation {
	
	private List<SimulationState> listStates;
	private int lineNb;
	private int columnNb;
	private StructureParts structureParts;
	
	
	//Constructeur
	public Simulation(ConfigureStructure structConfig, StructureParts structureParts) {
		this.listStates = new ArrayList<SimulationState>();
		this.lineNb = structConfig.lineNb;
		this.columnNb = structConfig.columnNb;
		this.structureParts = structureParts;
	}
	

	/**
	 * Initialisation de la première étape et exécution de l'avancement de la simulation pas à pas.
	 * 
	 * @param structureParts
	 * @param movingParts
	 * 
	 */
	//
	
	public void run() {
			
		boolean termination = false;
		int currentState = 0;
		
		SimulationState initState = new SimulationState(currentState, this.lineNb, this.columnNb, this.structureParts);
		listStates.add(initState);
		
		while(termination == false) {
			//Execute simulation step by step.
			currentState++;
			listStates.add(new SimulationState(currentState, this.lineNb, this.columnNb, this.structureParts)); //Add simulation state to list
			termination = listStates.get(currentState).generate(listStates.get(currentState-1)); //Generate current state from previous State
		}
	}



	/**
	 * Get a state of the simulation at the given index
	 * @param index
	 * @return SimulationState object
	 */
	public SimulationState getState(int index) {
		return this.listStates.get(index);
	}
	
}
