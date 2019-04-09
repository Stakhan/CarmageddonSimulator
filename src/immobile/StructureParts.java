package immobile;

import java.util.List;

import enumeration.Orientation;
import immobile.lights.TrafficLightSystem;
import immobile.structures.Road;
import model.ConfigureStructure;

public class StructureParts {
	
	private List<Road> listRoads;
	private TrafficLightSystem trafficLightSystem;
	
	public StructureParts(ConfigureStructure structConfig) {
		listRoads.add(new Road(structConfig.columnNb, structConfig.laneSize, structConfig.sideWalkSize, Orientation.Horizontal, 1, structConfig.bidirectional));
		listRoads.add(new Road(structConfig.columnNb, structConfig.laneSize, structConfig.sideWalkSize, Orientation.Vertical, 1, structConfig.bidirectional));
	}
}
