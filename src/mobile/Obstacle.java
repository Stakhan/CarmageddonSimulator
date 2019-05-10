package mobile;

import enumeration.ObstacleType;

public class Obstacle {
	private Integer[] position;
	private ObstacleType type;
	
	public Obstacle(Integer[] position, ObstacleType type) {
		this.position = position;
		this.type = type;
	}
	public Obstacle() {
		this.position = null;
		this.type = null;
	}
	
	@Override
	public String toString() {
		return this.type + " at " + this.position[0] + "," +this.position[1];
	}
}
