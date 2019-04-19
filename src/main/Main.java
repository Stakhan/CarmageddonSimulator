package main;

import java.util.List;

import display.Window;
import engine.Simulation;
import enumeration.Profil;
import immobile.StructureParts;
import immobile.structures.Lane;
import mobile.Car;
import model.Cell;
import model.ConfigureStructure;



public class Main {
	public static void main(String[] args) {
		
		ConfigureStructure structConfig = new ConfigureStructure(300, 500);
		StructureParts structureParts1 = new StructureParts(structConfig);
		Simulation simulation1 = new Simulation(structConfig, structureParts1);
		simulation1.run();
		

		//simulation1.getState(0).writeToFile("simulation-state1.grid");

		Lane lane = structureParts1.getRoad(0).getLane(0);

		Car car = new Car("voiture", 5, 3, Profil.respectful, 50.0, 70.0, 1.0, 0, 0, lane);
		car.initializeCar(simulation1.getState(0));

			
		simulation1.getState(0).writeToFile("simulation-state1.grid");

		
		//Affichage avec Java2d
		Window window1 = new Window(structConfig, simulation1.getState(0));
		
	}
}