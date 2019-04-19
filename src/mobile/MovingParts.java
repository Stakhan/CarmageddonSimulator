package mobile;

import java.util.ArrayList;
import java.util.List;

import enumeration.Profil;
import immobile.StructureParts;
import immobile.structures.Lane;
import immobile.structures.Road;
import immobile.structures.Structure;
import model.SimulationState;

public abstract class MovingParts {
	
	private List<Car> listCars;
	private List<Pedestrian> listPedestrians;
	
	/**
	 * Constructor
	 */
	public MovingParts(StructureParts structureParts) {
		listCars = new ArrayList<Car>();
		listPedestrians = new ArrayList<Pedestrian>();
		populate(structureParts.getRoad(0), structureParts);
	}
	
	/**
	 * Add MovingObjects to the simulation
	 */
	public void populate(Road road, StructureParts structureParts) {
		//Only one car for now
		this.listCars.add(new Car("voiture", 5, 3, Profil.crazy, 0, 100, 55, road.getLane(0), structureParts));
		
	}
}
