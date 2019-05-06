package mobile;

import java.util.ArrayList;
import java.util.List;

import engine.Simulation;
import enumeration.Profil;
import immobile.StructureParts;


public class MovingParts {
	
	private Simulation simulation;
	private List<Car> listCars;
	private List<Pedestrian> listPedestrians;
	
	/**
	 * Constructor
	 */
	public MovingParts(Simulation simulation, StructureParts structureParts) {
		listCars = new ArrayList<Car>();
		listPedestrians = new ArrayList<Pedestrian>();
		this.simulation = simulation;
		
	}
	
	/**
	 * Add MovingObjects to the simulation
	 */
	public void generate() {
		//TESTING ONLY : one car for each direction of each lane
		listCars.add(new Car(this, "voiture", 5, 3, Profil.respectful, 0, 2, 10, this.simulation.getStructureParts().getRoad(0).getLane(0)));
		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 0, 2, 10, this.simulation.getStructureParts().getRoad(0).getLane(1)));
		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 0, 2, 10, this.simulation.getStructureParts().getRoad(1).getLane(0)));
		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 0, 2, 10, this.simulation.getStructureParts().getRoad(1).getLane(1)));
		
		//TESTING ONLY: adding a pedestrian on the Road
		int[] position = {92,92};
		listPedestrians.add(new Pedestrian(position));
		
	}
	
	//Getters
	
	public List<Car> getListCars() {
		return listCars;
	}
	
	public List<Pedestrian> getListPedestrians() {
		return listPedestrians;
	}
	
	public Simulation getSimulation() {
		return simulation;
	}
	public Car getCar(int index) {
		return listCars.get(index);
	}
}
