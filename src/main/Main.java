package main;


import display.StructWindow;
import display.Window;
import engine.Simulation;
import model.ConfigureFlow;
import model.ConfigureStructure;


public class Main {
	public static void main(String[] args) {
		
		// *** HOW TO USE ***
		// Display the Simulation :
		/*
		ConfigureStructure structConfig = new ConfigureStructure(100, 700, true);
		
		Simulation simulation1 = new Simulation(structConfig);
		simulation1.init();
		
		// Adding the flow to the simulation
		simulation1.setConfiguredFlow(new ConfigureFlow(simulation1));
		
		//Writting simulation state to file
		//simulation1.getState(0).writeToFile("simulation-state1.grid");
		
	
		new Window(structConfig, simulation1);
		 */
		
		
		// Here is the final version, with a starting interface
		new StructWindow();

		
	}
}


