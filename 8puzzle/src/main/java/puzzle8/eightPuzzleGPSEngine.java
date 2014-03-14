package puzzle8;

import gps.GPSEngine;
import gps.GPSNode;

import java.util.HashSet;

public class eightPuzzleGPSEngine extends GPSEngine{

	private HashSet<Integer> set = new HashSet<Integer>();
	@Override
	public void addNode(GPSNode node) {
		
		System.out.println(node.getState());
		if(!set.contains(node.getState().hashCode())){
			set.add(node.getState().hashCode());
			this.addOpenNode(node);
		}
	}

}
