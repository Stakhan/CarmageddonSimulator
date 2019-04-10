package main;

import engine.Simulation;
import immobile.StructureParts;
import model.ConfigureStructure;

public class Main {
	public static void main(String[] args) {
		
		ConfigureStructure structConfig = new ConfigureStructure(50);
		StructureParts structureParts1 = new StructureParts(structConfig);
		Simulation simulation1 = new Simulation(structConfig, structureParts1);
		simulation1.run();
		simulation1.getState(1).writeToFile("simulation-state1.grid");
		
	}
}