package main;

import display.Window;
import engine.Simulation;
import immobile.StructureParts;
import mobile.Car;
import model.ConfigureStructure;

public class Main {
	public static void main(String[] args) {
		
		ConfigureStructure structConfig = new ConfigureStructure(350);
		StructureParts structureParts1 = new StructureParts(structConfig);
		Simulation simulation1 = new Simulation(structConfig, structureParts1);
		simulation1.run();
		Car car = new Car(350, "Renault", 1, 1, 0, 0);
		
		simulation1.getState(0).writeToFile("simulation-state1.grid");
		Window window1 = new Window(structConfig, simulation1.getState(0));
		
	}
}