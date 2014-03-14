package puzzle8;

import gps.GPSEngine;
import gps.GPSNode;

public class eightPuzzleGPSEngine extends GPSEngine{

	@Override
	public void addNode(GPSNode node) {
		this.addOpenNode(node);
	}

}
