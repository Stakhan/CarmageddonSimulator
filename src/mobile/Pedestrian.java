package mobile;


import java.util.List;

import enumeration.MobileType;

public class Pedestrian extends MobileObject{

	
	private List<enumeration.OrientedDirection> path;

	public Pedestrian(MovingParts movingParts, int[] position) {
		
		super(2, 3, position);
		computeCoverage();
		
	}
	
	public void computeCoverage(){
		
		this.objectCoverage.clear();
		
		Integer[] pos = {this.position[0],this.position[1]};
		objectCoverage.add(pos);
		System.out.println();
	}
	
	
	public void nextStep() {
		this.computeCoverage();
		//System.out.println("Car "+this+" looking :"+this.look(10*this.length));
	}
	
	@Override
	public MobileType getType() {
		return MobileType.Pedestrian;
	}
}
