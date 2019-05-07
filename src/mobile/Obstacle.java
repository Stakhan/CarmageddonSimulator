package mobile;

import enumeration.ObstacleType;

public class Obstacle {
	private int distance;
	private ObstacleType type;
	
	public Obstacle(int distance, ObstacleType type) {
		this.distance = distance;
		this.type = type;
	}
	public Obstacle() {
		this.distance = 0;
		this.type = null;
	}
	
	@Override
	public String toString() {
		return this.type+" at "+this.distance;
	}
}
