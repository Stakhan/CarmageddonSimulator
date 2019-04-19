package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import immobile.structures.Lane;
import model.Cell;
import model.ConfigureStructure;
import model.SimulationState;

public class Panel extends JPanel{

	private int wUnit;
	private int hUnit;
	private ConfigureStructure structConfig;
	private SimulationState state;

	public Panel(ConfigureStructure structConfig, SimulationState state){
		this.structConfig = structConfig;
		this.state = state;
	}

	public void defineUnits(ConfigureStructure structConfig) {
		this.wUnit = (int) this.getWidth()/structConfig.columnNb;
		this.hUnit = (int) this.getHeight()/structConfig.lineNb;
	}

	@Override
	public void paintComponent(Graphics g) {
		//Définition de la taille d'un pixel en fonction de la taille de la simulation
		defineUnits(structConfig);
		super.paintComponent(g); // Appel de la methode paintComponent de la classe mere
		// Graphics est un objet fourni par le systeme qui est utilise pour dessiner les composant du conteneur
		Graphics2D g2d = (Graphics2D) g;

		Cell[][] grid = state.getGrid();
		for(int i=0; i<structConfig.columnNb; i++) {
			for(int j=0; j<structConfig.lineNb; j++) {
				if(grid[i][j].getcontainedRoads().size() != 0) {

					if(grid[i][j].getContainedStructures(0) instanceof immobile.structures.SideWalk) {
						g2d.setPaint(Color.gray); 
						g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
					}
					else if(grid[i][j].getContainedStructures(0) instanceof Lane) {
						g2d.setPaint(Color.black); 
						g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
					}
				}
				else if (grid[i][j].getcontainedRoads().size() == 0) {
					g2d.setPaint(Color.green); 
					g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
				}
			}
		}


	}


}
