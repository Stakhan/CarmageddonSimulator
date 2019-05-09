package mobile;

import java.util.ArrayList;
import java.util.List;

import engine.Simulation;
import enumeration.OrientedDirection;
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
		listCars.add(new Car(this, "voiture", 5, 3, Profil.respectful, 2, 17, this.simulation.getStructureParts().getRoad(0).getLane(0)));
		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 3, 10, this.simulation.getStructureParts().getRoad(0).getLane(1)));
		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 4, 10, this.simulation.getStructureParts().getRoad(1).getLane(0)));
		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 5, 10, this.simulation.getStructureParts().getRoad(1).getLane(1)));
		
		//TESTING ONLY: adding a pedestrian on the Road

		int[] position = {0,94};
		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.WE, this.simulation.getStructureParts().getRoad(0).getSideWalk(0)));
		listPedestrians.add(new Pedestrian(this, 3, 3, OrientedDirection.NS, this.simulation.getStructureParts().getRoad(1).getSideWalk(0)));
		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.EW, this.simulation.getStructureParts().getRoad(0).getSideWalk(0)));
		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.SN, this.simulation.getStructureParts().getRoad(1).getSideWalk(0)));
		
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
