package deeptrip.ai.engine;

import gps.GPSEngine;
import gps.GPSNode;
import gps.SearchStrategy;
import gps.api.GPSProblem;
import gps.exception.NotApplicableException;
import org.apache.log4j.Logger;

import java.util.HashSet;

public class DeeptripAIEngine extends GPSEngine {
	private static final Logger logger = Logger.getLogger(DeeptripAIEngine.class);
	private HashSet<Integer> set = new HashSet<>();

    private static final int INFINITE = -1;
    private int maxDepth = INFINITE;

	@Override
	public void addNode(GPSNode node) throws NotApplicableException {

		System.out.println(node.getState());
		if (logger.isTraceEnabled()) logger.trace(node.getState());
		if (!set.contains(node.getState().hashCode())) {
			set.add(node.getState().hashCode());
			SearchStrategy strategy = getStrategy();


            switch (strategy) {
            case DFS:
                if (maxDepth != INFINITE) {
                    if (node.getDepth() >= maxDepth) {
                        throw new NotApplicableException();
                    }
                }
                this.addOpenNodeFirst(node);
                break;
            case BFS:
                this.addOpenNode(node);
                break;
            case AStar:
                this.addOpenNodeA(node);
                break;
            case Greedy:
                this.addOpenNodeGreedy(node);
                break;
            default:
                throw new IllegalArgumentException();
            }
        }
    }

    public void engine(GPSProblem problem, SearchStrategy searchStrategy, int maxDepth) {
        this.maxDepth = maxDepth;
        this.engine(problem, searchStrategy);
    }
}