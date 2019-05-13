package display;

import java.awt.BasicStroke;

import java.awt.Color;
import enumeration.TrafficColor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import engine.Simulation;
import enumeration.MobileType;
import enumeration.Profil;
import enumeration.StructureType;

import enumeration.ObstacleType;
import enumeration.Orientation;
import enumeration.OrientedDirection;
import enumeration.ObstacleType;
import immobile.lights.TrafficLight;

import immobile.structures.Lane;
import immobile.structures.Road;
import immobile.structures.Structure;
import mobile.Car;
import mobile.Pedestrian;
import model.Cell;
import model.ConfigureStructure;
import model.SimulationState;
import stats.Statistics;

public class GridPanel extends JPanel implements KeyListener{

	/**
	 * This panel displays the simulation
	 */
	private static final long serialVersionUID = 1L;
	private int wUnit;
	private int hUnit;
	private ConfigureStructure structConfig;
	private Simulation simulation;
	private SimulationState displayState;
	private BufferedImage greenLight;
	private BufferedImage yellowLight;
	private BufferedImage redLight;
	private boolean continueRunning;
	

	public GridPanel(ConfigureStructure structConfig, Simulation simulation){
		this.structConfig = structConfig;
		this.simulation = simulation;
		this.displayState = this.simulation.getLastState();
		continueRunning = false;
		
		try {
			initImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setFocusable(true); // sinon par defaut le panel n’a pas le focus : on ne peut pas interagir avec
	    this.requestFocus();
		this.addKeyListener(this); // on declare que this ecoute les evenements clavier

	}

	/**
	 * Définition de la taille d'un pixel en fonction de la taille de la simulation
	 * @param structConfig
	 */
	public void defineUnits(ConfigureStructure structConfig) {
		this.wUnit = (int) this.getWidth()/structConfig.columnNb;
		this.hUnit = (int) this.getHeight()/structConfig.lineNb;
	}
	
	public void initImages() throws IOException {

		List<URL> urls = new ArrayList<URL>();
		urls.add(getClass().getResource("images/light_green.png"));
		urls.add(getClass().getResource("images/light_yellow.png"));
		urls.add(getClass().getResource("images/light_red.png"));
		
		if (urls.get(0) == null) {
	        throw new FileNotFoundException();
	    }
	    this.greenLight = ImageIO.read(urls.get(0));
	    if (urls.get(1) == null) {
	        throw new FileNotFoundException();
	    }
	    this.yellowLight = ImageIO.read(urls.get(1));
	    if (urls.get(2) == null) {
	        throw new FileNotFoundException();
	    }
	    this.redLight = ImageIO.read(urls.get(2));
	}
	
	public BufferedImage rotate(BufferedImage image, double angle) {
		double rotationRequired = Math.toRadians(angle);
		double locationX = image.getWidth(null) / 2;
		double locationY = image.getHeight(null) / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		return op.filter((BufferedImage) image, null);
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
		
		
		
		Cell[][] grid = this.displayState.getGrid();
		
		
		
		
		//Go over all cells of the grid
		for(int i=0; i<structConfig.columnNb; i++) {
			for(int j=0; j<structConfig.lineNb; j++) {	
				
				if(grid[i][j].getcontainedRoads().size() != 0) { //Test if cell contains road
					
					if(grid[i][j].getContainedMobileObjects().size() != 0) { //Test if it contains a MobileObject
						
						if(grid[i][j].contains(MobileType.Car)) { //Test if it contains a Car
								
							int[] position = grid[i][j].getContainedMobileObjects().get(0).getPosition(); //Get central position of car
							if(position[0] == i && position[1] == j) { //Check if cell is center of car
								//g2d.setPaint(Color.pink); //Paint in pink in that case
							}
							else {
								g2d.setColor(new Color(245, 0, 0)); 
								//g2d.setPaint(Color.red); //Rest of car should be painted in red
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
						
						if(grid[i][j].getContainedLights().size() != 0) {
							g2d.setPaint(Color.orange); 
							//g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
						}
						if(grid[i][j].contains(StructureType.SideWalk) && grid[i][j].contains(StructureType.Lane)) { //Test if it contains a Lane and a SideWalk (in that case it should be considered a Lane)
							//Paint cell in pink
							g2d.setPaint(Color.white); 
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
									g2d.setPaint(Color.lightGray);
								}
								else {
									//Paint cell in black
									g2d.setPaint(Color.black);
								}
							}
							g2d.setPaint(Color.darkGray);
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
					g2d.setColor(new Color(51, 250, 51)); 		// DARK GREEN
					g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
					if(border) {
						//Paint a black border around cell
						g2d.setPaint(Color.black);
						g2d.drawRect(j*wUnit, i*hUnit, wUnit, hUnit);
					}
				}			
			}
		}
		
		
		// Testin the method below
		for (Road road : simulation.getStructureParts().getListRoads()) {
			for (Lane lane : road.getListLanes()) {
				int[] position = lane.getIntersectionPosition();
				int i = position[0];
				int j = position[1];
				g2d.setPaint(Color.yellow);
				g2d.drawRect(j*wUnit, i*hUnit, wUnit, hUnit);
			}
		}
		//for (Lane lane : simulation.getStructureParts().get)
		
		
		/*
		// TESTING ONLY : show intersection points with index road and lane to generalize a method
		int x1;
		int y1;
		//0,0
		x1 = simulation.getStructureParts().getRoad(0).getPosition() + simulation.getStructureParts().getRoad(0).getLaneSize() + 1;
		y1 = simulation.getStructureParts().getRoad(0).getPosition() + simulation.getStructureParts().getRoad(0).getLaneSize();
		g2d.setPaint(Color.yellow);
		g2d.fillRect(x1*wUnit, y1*hUnit, wUnit, hUnit);
		
		//1,1
		x1 = simulation.getStructureParts().getRoad(1).getPosition() + simulation.getStructureParts().getRoad(1).getLaneSize()+ 1;
		y1 = simulation.getStructureParts().getRoad(1).getPosition() + simulation.getStructureParts().getRoad(1).getLaneSize()*(1+1) ;
		g2d.setPaint(Color.yellow);
		g2d.fillRect(x1*wUnit, y1*hUnit, wUnit, hUnit);
		
		//1,0
		x1 = simulation.getStructureParts().getRoad(1).getPosition() + simulation.getStructureParts().getRoad(1).getLaneSize()*(1+1)+ 1;
		y1 = simulation.getStructureParts().getRoad(1).getPosition() + simulation.getStructureParts().getRoad(1).getLaneSize() ;
		g2d.setPaint(Color.yellow);
		g2d.fillRect(x1*wUnit, y1*hUnit, wUnit, hUnit);
		
		//0,1
		x1 = simulation.getStructureParts().getRoad(1).getPosition() + simulation.getStructureParts().getRoad(1).getLaneSize()*(1+1)+ 1;
		y1 = simulation.getStructureParts().getRoad(1).getPosition() + simulation.getStructureParts().getRoad(1).getLaneSize()*(1+1) ;
		g2d.setPaint(Color.yellow);
		g2d.fillRect(x1*wUnit, y1*hUnit, wUnit, hUnit);
		*/
		//System.out.println("TRAFFIC LIGHT : " + simulation.getStructureParts().getStructGrid()[58][53].getContainedLights().size());
	
		//TESTING ONLY: Painting view span over
		g2d.setPaint(Color.yellow);
		for (Car car : this.simulation.getMovingParts().getListCars()) {
			if (!car.inGarage()) {
				List<Integer[]> viewList = car.getVision().getViewList();
				for (Integer[] coord : viewList) {
					
					//g2d.fillRect((coord[1])*wUnit, (coord[0])*hUnit, wUnit, hUnit);
				}
			}
		}
		
		
		
		
		//Display traffic lights
		
		//get current color for road 0
		enumeration.TrafficColor current = this.displayState.getTrafficLightSystem().getListLights().get(0).getCurrentColor(); 
		BufferedImage imageLight = this.greenLight;
		if (current == enumeration.TrafficColor.Green) {
			imageLight = this.greenLight;
		}
		else if (current == enumeration.TrafficColor.Yellow) {
			imageLight = this.yellowLight;
		}
		else if (current == enumeration.TrafficColor.Red) {
			imageLight = this.redLight;
		}
		
		//ROAD 0
		//light in true direction
		int x = (int) (structConfig.lineNb*0.45*wUnit);
		int y = (int) (structConfig.columnNb*0.542*hUnit);
		int xSize = (int)(imageLight.getWidth(null)*0.15);
		int ySize = (int)(imageLight.getHeight(null)*0.15);
		AffineTransform backup = g2d.getTransform();
		AffineTransform a = AffineTransform.getRotateInstance(Math.toRadians(90), x, y);
		g2d.setTransform(a);
		g2d.drawImage(imageLight, x, y, xSize, ySize, null);
		g2d.setTransform(backup);
		
		//light in false direction
		x = (int) (structConfig.lineNb*0.555*wUnit);
		y = (int) (structConfig.columnNb*0.457*hUnit);
		xSize = (int)(imageLight.getWidth(null)*0.15);
		ySize = (int)(imageLight.getHeight(null)*0.15);
		a = AffineTransform.getRotateInstance(Math.toRadians(-90), x, y);
		g2d.setTransform(a);
		g2d.drawImage(imageLight, x, y, xSize, ySize, null);
		g2d.setTransform(backup);
			
		//get current color for road 1
		current = this.displayState.getTrafficLightSystem().getListLights().get(1).getCurrentColor(); 
		if (current == enumeration.TrafficColor.Green) {
			imageLight = this.greenLight;
		}
		else if (current == enumeration.TrafficColor.Yellow) {
			imageLight = this.yellowLight;
		}
		else if (current == enumeration.TrafficColor.Red) {
			imageLight = this.redLight;
		}
		
		//ROAD 1
		//light in true direction
		x = (int) (structConfig.lineNb*0.46*wUnit);
		y = (int) (structConfig.columnNb*0.45*hUnit);
		xSize = (int)(imageLight.getWidth(null)*0.15);
		ySize = (int)(imageLight.getHeight(null)*0.15);
		a = AffineTransform.getRotateInstance(Math.toRadians(180), x, y);
		g2d.setTransform(a);
		g2d.drawImage(imageLight, x, y, xSize, ySize, null);
		g2d.setTransform(backup);
		
		//light in false direction
		x = (int) (structConfig.lineNb*0.548*wUnit);
		y = (int) (structConfig.columnNb*0.552*hUnit);
		xSize = (int)(imageLight.getWidth(null)*0.15);
		ySize = (int)(imageLight.getHeight(null)*0.15);
		
		g2d.drawImage(imageLight, x, y, xSize, ySize, null);
		g2d.setTransform(backup);
		
		// TESTING ONLY: Painting car position
//		int i= 52;
//		int j = 30;
//		g2d.setPaint(Color.blue);
//		g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
//		 i= 52;
//		 j = 10;
//		g2d.setPaint(Color.blue);
//		g2d.fillRect(j*wUnit, i*hUnit, wUnit, hUnit);
		
		

	}
	
	public void update() {
		

		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) { // pour implementer KeyListener

		int key = e.getKeyCode();
		
		if ((key == KeyEvent.VK_SPACE)) {
			 //Creating the SwingWorker   
		    ComputeGridWorker computeGridWorker= new ComputeGridWorker(this);
		    if(continueRunning == false) {
		    	continueRunning = true;
		    	//Launching the SwingWorker
			    computeGridWorker.execute();
		    }
		    else {
		    	continueRunning = false;
		    }
		    
			
		}
		if ((key == KeyEvent.VK_S)) {
			continueRunning = false;
		}
		if ((key == KeyEvent.VK_LEFT)) { // cas fleche de gauche
			if (this.displayState.getStep() > 0) { //Make sure it's not the first state
				this.displayState = this.simulation.getState(this.displayState.getStep()-1);
				repaint();
				System.out.println("step "+this.displayState.getStep()+": "+this.simulation.getState(this.displayState.getStep()).getGrid().toString());
			}

		}
		if ((key == KeyEvent.VK_RIGHT)) {
			//System.out.println(this.simulation.getListStates().size()+" et "+this.displayState.);
			if (this.displayState.getStep() < this.simulation.getListStates().size()-1) { //test if next step has already been computed
				this.displayState = this.simulation.getState(this.displayState.getStep()+1);
			}
			else { //if not, compute it
				this.simulation.nextState();
				this.displayState = this.simulation.getLastState();

			}
			repaint();
			System.out.println("step "+this.displayState.getStep()+": "+this.simulation.getLastState().getGrid().toString());

		}
		if ((key == KeyEvent.VK_UP)) { 
			this.displayState = new SimulationState(this.simulation, -1);
			repaint();
			System.out.println("hop");

		}
		if ((key == KeyEvent.VK_DOWN)) {
			for(SimulationState state : this.simulation.getListStates()) {
				System.out.println(state.getGrid().toString());
			}
		}
		if ((key == KeyEvent.VK_C)) {
			if(this.simulation.getStructureParts().getRoad(0).getLane(1).testAvailability(5, this.displayState)) { //Test if room available for poping
				this.simulation.getMovingParts().getListCars().add(new Car(this.simulation.getMovingParts(), 5, 3, Profil.respectful, 0, 5, 10, this.simulation.getStructureParts().getRoad(0).getLane(1)));
				this.simulation.getMovingParts().getLastCar().nextStep();
				this.simulation.getMovingParts().getLastCar().draw(this.displayState.getGrid());
				repaint();
			}	
		}
		
//		if ((key == KeyEvent.VK_X)) {
//			if(this.simulation.getStructureParts().getRoad(0).getLane(1).testAvailability(5, this.displayState)) { //Test if room available for poping
//				this.simulation.getMovingParts().getListCars().add(new Car(this.simulation.getMovingParts(), "voiture", 5, 3, Profil.respectful, 0, 1, 10, this.simulation.getStructureParts().getRoad(1).getLane(1)));
//				this.simulation.getMovingParts().getLastCar().nextStep();
//				this.simulation.getMovingParts().getLastCar().draw(this.displayState.getGrid());
//				repaint();
//			}	
//		}
		
		
		if ((key == KeyEvent.VK_P)) {
			this.simulation.getMovingParts().getListPedestrians().add(new Pedestrian(this.simulation.getMovingParts(), 1, 1, OrientedDirection.WE, this.simulation.getStructureParts().getRoad(0).getSideWalk(0)));

		}
		if ((key == KeyEvent.VK_L)) {
			for (Car car : this.simulation.getMovingParts().getListCars()) {
				if(!car.inGarage()) {
					//System.out.println("Car "+this.simulation.getMovingParts().getListCars().indexOf(car)+" looking :"+car.getVision().look().toString());
				}
			}
		}
		if ((key == KeyEvent.VK_T)) {
			Cell[][] grid = null;
			if (this.simulation.getListStates().size() > 1) { //We need two states to get a previous state
				//Fetching previous state
				SimulationState previousState = this.simulation.getState(this.simulation.getLastState().getStep());
				//Fetching grid of previous step
				grid = previousState.getGrid();
			}
			else { //In case it is the first state
				grid = this.simulation.getMovingParts().getSimulation().getStructureParts().getStructGrid();
			}
			int i = 52;
			int j = 10;
			System.out.println(i+","+j+": "+grid[i][j].contains(MobileType.Car));
		}
		
	}
			
	
	// GETTERS
	public SimulationState getSimulationState() {
		return displayState;
	}
	
	public Simulation getSimulation() {
		return simulation;
	}
	public SimulationState getDisplayState() {
		return displayState;
	}

	public boolean getContinueRunning() {
		return this.continueRunning;
	}
	
	// SETTERS
	public void setSimulationState(SimulationState simulationState) {
		this.displayState = simulationState;
	}
	public void setDisplayState(SimulationState displayState) {
		this.displayState = displayState;
	}
	public void setContinueRunning(boolean continueRunning) {
		this.continueRunning = continueRunning;
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
