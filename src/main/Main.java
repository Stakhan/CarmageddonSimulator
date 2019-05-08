package main;

import display.Window;
import engine.Simulation;
import model.ConfigureStructure;



public class Main {
	public static void main(String[] args) {
		
		ConfigureStructure structConfig = new ConfigureStructure(200, 700, true);
		
		Simulation simulation1 = new Simulation(structConfig);
		simulation1.init();
		
		

		//Writting simulation state to file
		//simulation1.getState(0).writeToFile("simulation-state1.grid");
		
		
		//Affichage avec Java2d
		new Window(structConfig, simulation1);
		
	}
}


