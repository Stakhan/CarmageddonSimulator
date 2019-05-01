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
		
		ConfigureStructure structConfig = new ConfigureStructure(300, 700, true);
		
		Simulation simulation1 = new Simulation(structConfig);
		simulation1.init();
		
		

		//Writting simulation state to file
		//simulation1.getState(0).writeToFile("simulation-state1.grid");
		
		
		//Affichage avec Java2d
		Window window1 = new Window(structConfig, simulation1);
		
	}
}