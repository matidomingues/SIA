package puzzle8;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.util.ArrayList;
import java.util.List;

public class eightPuzzleGPSProblem implements GPSProblem{

	private final eightPuzzleGPSState initialState;
	private final eightPuzzleGPSState goalState;
	private final List<GPSRule> rules;
	
	public eightPuzzleGPSProblem(eightPuzzleGPSState initialState,
			eightPuzzleGPSState goalState) {
		this.initialState = initialState;
		this.goalState = goalState;
		this.rules = new ArrayList<GPSRule>(4);
		this.rules.add(new moveDownGPSRule());
		this.rules.add(new moveLeftGPSRule());
		this.rules.add(new moveRightGPSRule());
		this.rules.add(new moveUpGPSRule());
	}
	public GPSState getInitState() {
		return initialState;
	}

	public GPSState getGoalState() {
		return goalState;
	}

	public List<GPSRule> getRules() {
		return this.rules;
	}

	public Integer getHValue(GPSState state) {
		return 0;
	}

}
