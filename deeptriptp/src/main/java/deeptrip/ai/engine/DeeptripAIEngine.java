package deeptrip.ai.engine;

import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;
import gps.api.GPSProblem;
import gps.exception.NotApplicableException;

public class DeeptripAIEngine extends GPSEngine {

    private static final int INFINITE = -1;
    private int maxDepth = INFINITE;

	@Override
	public void addNode(GPSNode node) throws NotApplicableException{
		
		System.out.println(node.getState());
		SearchStrategy strategy = getStrategy();
		
		//TODO falta ver lo de los estados repetidos
		switch (strategy) {
		case DFS:
            if (maxDepth != INFINITE) {
                if (node.getDepth() >= maxDepth) {
                    throw  new NotApplicableException();
                }
            }
			this.addOpenNodeFirst(node);
			break;
		case BFS:
			this.addOpenNode(node);
			break;
		case AStar:
			//TODO
			break;
        case Greedy:
            break;
		default:
			
		}

	}

    public void engine(GPSProblem problem, SearchStrategy strategy, int depth) {
        this.engine(problem, strategy);
    }
}
