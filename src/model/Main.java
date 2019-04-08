package model;

import engine.Simulation;

public class Main {
	public static void main(String[] args) {
		ConfigureStructure structConfig = new ConfigureStructure(350);
		Simulation simulation1 = new Simulation(structConfig);
		simulation1.run();
		System.out.println(simulation1.getState(1).toString());
	}
}
