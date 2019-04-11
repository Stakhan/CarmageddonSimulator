package model;

public class ConfigureStructure {
	
	public int intersectionNb;
	public int lineNb;
	public int columnNb;
	public int laneSize;
	public int sideWalkSize;
	public boolean bidirectional;
	
	//Constructeurs
	public ConfigureStructure() {
		this.intersectionNb = 1;
		this.lineNb = 350;
		this.columnNb = 350;
		this.laneSize = 5;
		this.sideWalkSize = 3;
	}
	
	public ConfigureStructure(int size) {
		this.intersectionNb = 1;
		this.lineNb = size;
		this.columnNb = size;
		this.laneSize = 5;
		this.sideWalkSize = 3;
	}
}
