
package engine;

import java.util.ArrayList;
import java.util.List;

import immobile.StructureParts;
import mobile.Car;
import mobile.MovingParts;
import model.ConfigureStructure;
import model.SimulationState;

public class Simulation {
	
	private List<SimulationState> listStates;
	private int lineNb;
	private int columnNb;
	private StructureParts structureParts;
	private MovingParts movingParts;
	
	
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
		listStates.add(initState);
		
	}
	
	public void nextState() {
		SimulationState lastState = this.getLastState(); //fetch last state
		listStates.add(lastState.nextState()); //compute next state and add it to the list of states
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
	
}
