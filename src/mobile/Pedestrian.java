package mobile;

import enumeration.MobileType;

public class Pedestrian extends MobileObject{

	
	public Pedestrian(int[] position) {
		
		super(3, 3, position);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MobileType getType() {
		return MobileType.Pedestrian;
	}
}
