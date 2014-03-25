package deeptrip.ai.heuristics;

import deeptrip.ai.states.DeeptripAIState;
import gps.api.GPSState;

public class HeuristicOne implements Heuristic {
	
	
	@Override
	public Integer getHValue(GPSState stateFrom, GPSState goalState) {
		if(! (stateFrom instanceof DeeptripAIState)){
			throw new IllegalArgumentException();
			
		}
		if(stateFrom.compare(goalState)){
			return 0;
		}
		DeeptripAIState state=(DeeptripAIState)stateFrom;
		
		
		// TODO falta implementar de manera posta
		return 1;
	}

}
