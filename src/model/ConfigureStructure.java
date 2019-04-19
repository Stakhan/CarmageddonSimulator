package model;

public class ConfigureStructure {
/**
 * This class acts as a container for all the configuration parameters of the simulation
 */
	public int intersectionNb;
	
	public int lineNb;
	public int columnNb;
	
	public int hDisplaySize;
	public int vDisplaySize;
	
	public int laneSize;
	public int sideWalkSize;
	
	public boolean bidirectional;
	
	//Constructors

	
	public ConfigureStructure(int simulationSize, int displaySize) {
		
		this.intersectionNb = 1;
		this.lineNb = simulationSize;
		this.columnNb = simulationSize;
		this.hDisplaySize = displaySize;
		this.vDisplaySize = displaySize;
		this.laneSize = 5;
		this.sideWalkSize = 3;
		this.bidirectional = false;
		System.out.println("laneSize: "+this.laneSize);
		System.out.println("sideWalkSize: "+this.sideWalkSize);

	}
	
}


