package deeptrip.ai.engine;

import java.util.HashSet;

import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;

public class DeeptripAIEngine extends GPSEngine {
	private HashSet<Integer> set = new HashSet<Integer>();

	@Override
	public void addNode(GPSNode node) {

		System.out.println(node.getState());
		if (!set.contains(node.getState().hashCode())) {
			set.add(node.getState().hashCode());
			this.addOpenNode(node);
			SearchStrategy strategy = getStrategy();

			switch (strategy) {
			case DFS: {
				this.addOpenNodeFirst(node);
				break;
			}
			case BFS: {
				this.addOpenNode(node);
				break;
			}
			case AStar: {
				// TODO
				break;
			}
			default: {

			}
			}

			
		
	}

}
}
