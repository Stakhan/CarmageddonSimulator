package mobile;

import enumeration.ObstacleType;

public class Obstacle {
	private Integer[] position;
	private ObstacleType type;
	private Object obstacleObj;
	private int distance;
	
	public Obstacle(Integer[] position, Object obstacleObj, ObstacleType type, int distance) {
		this.position = position;
		this.obstacleObj = obstacleObj;
		this.type = type;
		this.distance = distance;
	}
	public Obstacle(Integer[] position, ObstacleType type) {
		this.position = position;
		this.obstacleObj = null;
		this.type = type;
	}
	public Obstacle() {
		this.position = null;
		this.obstacleObj = null;
		this.type = ObstacleType.Empty;
	}
	
	@Override
	public String toString() {
		return this.type + " at " + this.position[0] + "," +this.position[1];
	}
	public ObstacleType getType(){
		return type;
	}
	public Integer[] getPosition() {
		return position;
	}
	public Object getObject() {
		return obstacleObj;
	}
	public int getDistance() {
		return distance;
	}
}
