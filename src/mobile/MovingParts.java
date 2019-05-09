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
//		listCars.add(new Car(this, "voiture", 5, 3, Profil.respectful, 0, 2, 20, this.simulation.getStructureParts().getRoad(0).getLane(0)));
//		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 0, 2, 10, this.simulation.getStructureParts().getRoad(0).getLane(1)));
//		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 0, 2, 10, this.simulation.getStructureParts().getRoad(1).getLane(0)));
//		listCars.add(new Car(this, "voiture", 5, 3, Profil.crazy, 0, 2, 10, this.simulation.getStructureParts().getRoad(1).getLane(1)));
		
		//TESTING ONLY: adding a pedestrian on the Road

		//On left sidewalk of each road
		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.WE, this.simulation.getStructureParts().getRoad(0).getSideWalk(0)));
//		listPedestrians.add(new Pedestrian(this, 3, 3, OrientedDirection.NS, this.simulation.getStructureParts().getRoad(1).getSideWalk(0)));
//		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.EW, this.simulation.getStructureParts().getRoad(0).getSideWalk(0)));
//		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.SN, this.simulation.getStructureParts().getRoad(1).getSideWalk(0)));
		
		//On right sidewalk of each road
//		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.WE, this.simulation.getStructureParts().getRoad(0).getSideWalk(1)));
//		listPedestrians.add(new Pedestrian(this, 3, 3, OrientedDirection.NS, this.simulation.getStructureParts().getRoad(1).getSideWalk(1)));
//		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.EW, this.simulation.getStructureParts().getRoad(0).getSideWalk(1)));
//		listPedestrians.add(new Pedestrian(this, 1, 1, OrientedDirection.SN, this.simulation.getStructureParts().getRoad(1).getSideWalk(1)));
				
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
	public Car getLastCar() {
		return listCars.get(listCars.size()-1);
	}
}
