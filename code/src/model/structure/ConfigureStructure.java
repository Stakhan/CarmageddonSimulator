package model.structure;

public class ConfigureStructure {
	
	public int intersectionNb;
	public int lineNb;
	public int columnNb;
	
	//Constructeurs
	public ConfigureStructure() {
		this.intersectionNb = 1;
		this.lineNb = 350;
		this.columnNb = 350;
	}
	
	public ConfigureStructure(int size) {
		this.intersectionNb = 1;
		this.lineNb = size;
		this.columnNb = size;
	}
}
