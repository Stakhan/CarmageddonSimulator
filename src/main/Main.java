package main;

import java.util.List;

import display.Window;
import engine.Simulation;
import enumeration.Profil;
import immobile.StructureParts;
import immobile.structures.Lane;
import mobile.Car;
import mobile.MovingParts;
import model.Cell;
import model.ConfigureStructure;
import model.SimulationState;



public class Main {
	public static void main(String[] args) {
		
		ConfigureStructure structConfig = new ConfigureStructure(100, 700, true);
		StructureParts structureParts1 = new StructureParts(structConfig);
		System.out.println(structureParts1.getRoad(0).getLane(0).toString());

		//MovingParts movingParts1 = new MovingParts(structureParts1);
		Car car1 = new Car("voiture", 5, 3, Profil.crazy, 0, 100, 55, structureParts1.getRoad(0).getLane(0), structureParts1);
		Car car2 = new Car("voiture", 5, 3, Profil.crazy, 0, 100, 55, structureParts1.getRoad(0).getLane(1), structureParts1);
		Car car3 = new Car("voiture", 5, 3, Profil.crazy, 0, 100, 55, structureParts1.getRoad(1).getLane(0), structureParts1);
		Car car4 = new Car("voiture", 5, 3, Profil.crazy, 0, 100, 55, structureParts1.getRoad(1).getLane(1), structureParts1);
		Simulation simulation1 = new Simulation(structConfig, structureParts1);
		simulation1.run();
		

		//simulation1.getState(0).writeToFile("simulation-state1.grid");

		Lane lane = structureParts1.getRoad(0).getLane(0);

		//car.initializeCar(simulation1.getState(0));

			
		//simulation1.getState(0).writeToFile("simulation-state1.grid");
		
		//drawing a car for testing
		car1.draw(simulation1.getState(0).getGrid());
		car2.draw(simulation1.getState(0).getGrid());
		car3.draw(simulation1.getState(0).getGrid());
		car4.draw(simulation1.getState(0).getGrid());
		
		//Affichage avec Java2d
		Window window1 = new Window(structConfig, simulation1.getState(0));
		
	}
}