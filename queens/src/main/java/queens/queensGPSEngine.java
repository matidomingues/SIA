package queens;

import gps.GPSEngine;
import gps.GPSNode;

public class queensGPSEngine extends GPSEngine{

	@Override
	public void addNode(GPSNode node) {
		System.out.println(node.getState());
		this.addOpenNode(node);
		
	}

}
