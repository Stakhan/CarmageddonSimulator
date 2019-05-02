package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import engine.Simulation;
import enumeration.MobileType;
import enumeration.StructureType;
import immobile.structures.Lane;
import immobile.structures.Structure;
import mobile.Car;
import model.Cell;
import model.ConfigureStructure;
import model.SimulationState;
import sun.swing.MenuItemLayoutHelper.ColumnAlignment;

public class Panel extends JPanel implements KeyListener{

	/**
	 * This panel displays the simulation
	 */
	private static final long serialVersionUID = 1L;
	private int wUnit;
	private int hUnit;
	private ConfigureStructure structConfig;
	private Simulation simulation;
	private SimulationState displayState;

	public Panel(ConfigureStructure structConfig, Simulation simulation){
		this.structConfig = structConfig;
		this.simulation = simulation;
		this.displayState = this.simulation.getLastState();
		this.setFocusable(true); // sinon par defaut le panel n’a pas le focus : on ne peut pas interagir avec
		this.addKeyListener(this); // on declare que this ecoute les evenements clavier
		
//		JLabel label = new JLabel(this.simulation.getLastState().toString(), SwingConstants.RIGHT);
//		this.add(label);
	}

	/**
	 * Définition de la taille d'un pixel en fonction de la taille de la simulation
	 * @param structConfig
	 */
	public void defineUnits(ConfigureStructure structConfig) {
		this.wUnit = (int) this.getWidth()/structConfig.columnNb;
		this.hUnit = (int) this.getHeight()/structConfig.lineNb;
	}

	@Override
	public void paintComponent(Graphics g) {
		//Show grid border
		boolean border = false;
		
		defineUnits(structConfig);

		
		
		super.paintComponent(g); // Appel de la methode paintComponent de la classe mere
		// Graphics est un objet fourni par le systeme qui est utilise pour dessiner les composant du conteneur
		Graphics2D g2d = (Graphics2D) g;
				
		BasicStroke bs1 = new BasicStroke(1); // pinceau du contour : taille 1
		g2d.setStroke(bs1);		
		
		//Filling background
		g2d.setPaint(Color.blue); 
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Cell[][] grid = this.displayState.getGrid();
		
		//Go over all cells of the grid
		for(int i=0; i<structConfig.columnNb; i++) {
			for(int j=0; j<structConfig.lineNb; j++) {
				
				if(grid[i][j].getcontainedRoads().size() != 0) { //Test if cell contains road
					
					if(grid[i][j].getContainedMobileObjects().size() != 0) { //Test if it contains a MobileObject
						
						if(grid[i][j].contains(MobileType.Car)) { //Test if it contains a Car
							
							Cell position = grid[i][j].getContainedMobileObjects().get(0).getPosition(); //Get central position of car
								if(position.getX() == j+1 && position.getY() == i+1) { //Check if cell is center of car
									g2d.setPaint(Color.pink); //Paint in pink in that case
								}
								else {
									g2d.setPaint(Color.red); //Rest of car should be painted in red
								}
							
							//Paint cell
							g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
						}
						else if(grid[i][j].contains(MobileType.Pedestrian)) { //Test if it contains a Pedestrian
							//Paint cell in black
							g2d.setPaint(Color.blue); 
							g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
						}
					}

					else { //In case it doesn't contain a MobileObject
						if(grid[i][j].contains(StructureType.SideWalk) && grid[i][j].contains(StructureType.Lane)) { //Test if it contains a Lane and a SideWalk (in that case it should be considered a Lane)
							//Paint cell in black
							g2d.setPaint(Color.black); 
							g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
							if(border) {
								//Paint a white border around cell
								g2d.setPaint(Color.gray);
								g2d.drawRect(j*wUnit, i*hUnit, wUnit, hUnit);
							}
						}
						else if(grid[i][j].contains(StructureType.Lane)) { //Test if it contains a Lane
							for(Structure lane : grid[i][j].getContainedStructures()) {
								if(((Lane)lane).getDirection() == false) {
									//Paint cell in white
									g2d.setPaint(Color.white);
								}
								else {
									//Paint cell in black
									g2d.setPaint(Color.black);
								}
							}
							 
							g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
							if(border) {
								//Paint a white border around cell
								g2d.setPaint(Color.gray);
								g2d.drawRect(j*wUnit, i*hUnit, wUnit, hUnit);
							}
							
						}
						else if(grid[i][j].contains(StructureType.SideWalk)) { //Test if it contains a SideWalk
							//Paint cell in gray
							g2d.setPaint(Color.gray); 
							g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
							if(border) {
								//Paint a black border around cell
								g2d.setPaint(Color.black);
								g2d.drawRect(j*wUnit, i*hUnit, wUnit, hUnit);
							}
							
						}

					}
					
				}
				else if (grid[i][j].getcontainedRoads().size() == 0) { //In case it doesn't contain a road
					//Paint cell in green
					g2d.setPaint(Color.green); 
					g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
					if(border) {
						//Paint a black border around cell
						g2d.setPaint(Color.black);
						g2d.drawRect(j*wUnit, i*hUnit, wUnit, hUnit);
					}
					

				}
			}
		}
		

	}
	
	@Override
	public void keyPressed(KeyEvent e) { // pour implementer KeyListener
		
		int key = e.getKeyCode();
		
		if ((key == KeyEvent.VK_LEFT)) { // cas fleche de gauche
			this.displayState = this.simulation.getState(this.displayState.getStep()-1);
			
			System.out.println(this.simulation.getState(this.displayState.getStep()-1).getGrid().toString());
		}
		if ((key == KeyEvent.VK_RIGHT)) {
			this.simulation.nextState();
			this.displayState = this.simulation.getLastState();
			repaint();
			System.out.println(this.simulation.getLastState().getGrid().toString());

//			Cell position = this.simulation.getMovingParts().getListCars().get(0).getPosition();
//			System.out.println("number of states: "+this.simulation.getListStates().size());
//			System.out.println("position car1: "+position.getX()+","+position.getY());
//			position = this.simulation.getMovingParts().getCar(0).getPosition();
//			System.out.println(position.getX()+","+position.getY());
//			this.simulation.getLastState().writeToFile("simulation-state1.grid");
		}
		if ((key == KeyEvent.VK_UP)) { 
			System.out.println("=============");
			System.out.println(this.simulation.getStructureParts().getStructGrid().toString());
			System.out.println("=============");

		}
		if ((key == KeyEvent.VK_DOWN)) {
			for(SimulationState state : this.simulation.getListStates()) {
				System.out.println(state.getGrid().toString());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
