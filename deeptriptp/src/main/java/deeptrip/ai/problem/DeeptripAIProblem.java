package deeptrip.ai.problem;

import deeptrip.ai.heuristics.Heuristic;
import deeptrip.ai.rules.DeeptripAIRule;
import deeptrip.ai.states.DeeptripAIState;
import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.util.ArrayList;
import java.util.List;

public class DeeptripAIProblem implements GPSProblem{

	private final DeeptripAIState initialState;
	private final DeeptripAIState goalState;
	private final List<GPSRule> rules;
	private final Heuristic heuristic;
	
	public DeeptripAIProblem(DeeptripAIState initialState,
			DeeptripAIState goalState, Heuristic heuristic) {
		this.initialState = initialState;
		this.goalState = goalState;
		initialState.getBoard();
		this.rules = new ArrayList<>(4);
		int maxColumn=initialState.getBoard().getColumnsSize();
		
		int maxRow=initialState.getBoard().getRowsSize();
		for(int i=0;i<maxRow;i++){
			for(int j=1;j<maxColumn;j++){
				this.rules.add(new DeeptripAIRule(i, j));
			}
		}
		this.heuristic=heuristic;
		
	}
	
	
	public GPSState getInitState() {
		return this.initialState;
	}

	public GPSState getGoalState() {
		return this.goalState;
	}

	public List<GPSRule> getRules() {
		return this.rules;
	}

	public Integer getHValue(GPSState state) {
//		if(state.compare(goalState)){
//			return 0;
//		}
//		
//		// TODO falta implementar de manera posta
//		return 1;
		return heuristic.getHValue(state, this.goalState);
	}

}
