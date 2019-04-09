package immobile.structures;

public class Lane extends Structure {
	private boolean direction;
	private double speedLimit;

	
	public Lane(boolean direction, double speedLimit) {
		this.direction = direction;
		this.speedLimit = speedLimit;
	}
	public Lane(boolean direction) {
		this.direction = direction;
	}
	
	
	
}
