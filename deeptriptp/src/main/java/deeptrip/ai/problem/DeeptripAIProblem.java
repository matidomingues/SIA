package deeptrip.ai.problem;

import java.util.ArrayList;
import java.util.List;

import deeptrip.ai.rules.DowntripAIRule;
import deeptrip.ai.states.DowntripAIState;
import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

public class DeeptripAIProblem implements GPSProblem{

	private final DowntripAIState initialState;
	private final DowntripAIState goalState;
	private final List<GPSRule> rules;
	
	public DeeptripAIProblem(DowntripAIState initialState,
			DowntripAIState goalState) {
		this.initialState = initialState;
		this.goalState = goalState;
		initialState.getBoard();
		this.rules = new ArrayList<GPSRule>(4);
		int maxColumn=initialState.getBoard().getRow(0).size();
		
		int maxRow=initialState.getBoard().getRowsSize();
		for(int i=0;i<maxRow;i++){
			for(int j=1;j<maxColumn;j++){
				this.rules.add(new DowntripAIRule(i, j));
			}
		}
		
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
		// TODO falta implementar de manera posta
		return 0;
	}

}
