package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

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
		defineUnits(structConfig);
		super.paintComponent(g); // Appel de la methode paintComponent de la classe mere
	// Graphics est un objet fourni par le systeme qui est utilise pour dessiner les composant du conteneur
	Graphics2D g2d = (Graphics2D) g;
	
	Cell[][] grid = state.getGrid();
	for(int i=0; i<structConfig.columnNb; i++) {
		for(int j=0; j<structConfig.lineNb; j++) {
			
		}
	}
	g2d.setPaint(Color.black); // couleur de l’interieur
	g2d.fillRect(0, 0, wUnit, hUnit); // dessin de l’interieur
	
	
	}
	
	
}
