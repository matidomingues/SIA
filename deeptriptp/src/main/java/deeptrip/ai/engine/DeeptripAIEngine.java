package deeptrip.ai.engine;

import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;

public class DeeptripAIEngine extends GPSEngine {

	@Override
	public void addNode(GPSNode node) {
		SearchStrategy strategy = getStrategy();
		//TODO falta ver lo de los estados repetidos
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
			//TODO
			break;
		}
		default:{
			
		}
		
		}

	}

}
