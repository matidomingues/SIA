package gps;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotApplicableException;
import gps.exception.SolutionNotFoundException;

import java.util.*;

public abstract class GPSEngine {

	private List<GPSNode> open = new LinkedList<GPSNode>();

	private List<GPSNode> closed = new ArrayList<GPSNode>();

	private GPSProblem problem;

	// Use this variable in the addNode implementation
	private SearchStrategy strategy;

	public void engine(GPSProblem myProblem, SearchStrategy myStrategy) {

		problem = myProblem;
		strategy = myStrategy;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		boolean finished = false;
		boolean failed = false;
		long explosionCounter = 0;
		
		addOpenNode(rootNode);
		while (!failed && !finished) {
			if (open.size() <= 0) {
				failed = true;
			} else {
				GPSNode currentNode = open.remove(0);
				closed.add(currentNode);
				if (isGoal(currentNode)) {
					finished = true;
					System.out.println(currentNode.getSolution());
					System.out.println("Expanded nodes: " + explosionCounter);
					System.out.println("Border nodes: " + open.size());
					System.out.println("Generated nodes: "
							+ (open.size() + explosionCounter));
					System.out.println("Max depth: " + currentNode.getCost());
				} else {
					explosionCounter++;
					explode(currentNode);
				}
			}
		}

		if (finished) {
			System.out.println("OK! solution found!");
		} else if (failed) {
			System.err.println("FAILED! solution not found!");
			System.err.println("Expanded nodes: " + explosionCounter);
			System.out.println("Border nodes: " + open.size());
			System.out.println("Generated nodes: "
					+ (open.size() + explosionCounter));
			throw new SolutionNotFoundException();
		}
	}

	private boolean isGoal(GPSNode currentNode) {
		return currentNode.getState() != null
				&& currentNode.getState().compare(problem.getGoalState());
	}

	private boolean explode(GPSNode node) {
		if (problem.getRules() == null) {
			System.err.println("No rules!");
			return false;
		}

		for (GPSRule rule : problem.getRules()) {
			GPSState newState = null;
			try {
				newState = rule.evalRule(node.getState());
			} catch (NotApplicableException e) {
				// Do nothing
			}
			if (newState != null
					&& !checkBranch(node, newState)
					&& !checkOpenAndClosed(node.getCost() + rule.getCost(),
							newState)) {
				// TODO: Make it more efficient with Lazy Initialization. Maybe
				// not throwing exception and using if?
				try {
					GPSNode newNode = new GPSNode(newState, node.getCost()
							+ rule.getCost());
					newNode.setParent(node);
					addNode(newNode);
				} catch (NotApplicableException nae) {
					// Do nothing
				}
			}
		}
		return true;
	}

	private boolean checkOpenAndClosed(Integer cost, GPSState state) {
		for (GPSNode openNode : open) {
			if (openNode.getState().compare(state) && openNode.getCost() < cost) {
				return true;
			}
		}
		for (GPSNode closedNode : closed) {
			if (closedNode.getState().compare(state)
					&& closedNode.getCost() < cost) {
				return true;
			}
		}
		return false;
	}

	private boolean checkBranch(GPSNode parent, GPSState state) {
		if (parent == null) {
			return false;
		}
		return checkBranch(parent.getParent(), state)
				|| state.compare(parent.getState());
	}

	public SearchStrategy getStrategy() {
		return strategy;
	}

	public List<GPSNode> getOpenBranches() {
		return open;
	}

	public abstract void addNode(GPSNode node) throws NotApplicableException;

	protected void addOpenNode(GPSNode node) {
		if (!open.contains(node)) {
			this.open.add(node);
		}
	}

	protected void addOpenNodeFirst(GPSNode node) {
		if (!open.contains(node)) {
			this.open.add(0, node);
		}
	}

	protected void addOpenNodeA(GPSNode node) {
		open.add(node);
        Collections.sort(open, new Comparator<GPSNode>() {
            @Override
            public int compare(GPSNode node1, GPSNode node2) {
            	int hNode1=problem.getHValue(node1.getState());
            	int hNode2=problem.getHValue(node2.getState());
            	int fNode1 = hNode1 + node1.getCost();
    			int fNode2 = hNode2 + node2.getCost();
            	if (fNode1<fNode2) return -1;
                else if (fNode1==fNode2) return hNode1-hNode2;
                else return 1;
            }
        });
		
		
//		GPSProblem prob = this.problem;
//		boolean inserted = false;
//		for (int i = 0; i < open.size() && !inserted; i++) {
//			GPSNode n = open.get(i);
//			int fN = prob.getHValue(n.getState()) + n.getCost();
//			int fNode = prob.getHValue(node.getState()) + node.getCost();
//			if (fNode < fN) {
//				open.add(i, node);
//				inserted = true;
//			} else if (fNode == fN) {
//				int nCost = n.getCost();
//				int nodeCost = node.getCost();
//				while (nCost <= nodeCost && fN == fNode && i<open.size()-1) {
//					i++;
//					n = open.get(i);
//					fN = prob.getHValue(n.getState()) + n.getCost();
//					fNode = prob.getHValue(node.getState()) + node.getCost();
//					nCost = n.getCost();
//					nodeCost = node.getCost();
//				}
//				open.add(i, node);
//				inserted = true;
//			}
//
//		}
//		if(!inserted){
//			this.addOpenNode(node);
//		}

	}

	protected void addOpenNodeGreedy(GPSNode node) {
        open.add(node);
        Collections.sort(open, new Comparator<GPSNode>() {
            @Override
            public int compare(GPSNode o1, GPSNode o2) {
                if (o1.getDepth() < o2.getDepth()) return 1;
                else if (o1.getDepth() > o2.getDepth()) return -1;
                else return problem.getHValue(o1.getState())
                        - problem.getHValue(o2.getState());
            }
        });
	}
}
