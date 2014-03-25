package deeptrip.ai.heuristics;

import gps.api.GPSState;

public class HeuristicOne implements Heuristic {
	
	
	@Override
	public Integer getHValue(GPSState stateFrom, GPSState goalState) {
		if(stateFrom.compare(goalState)){
			return 0;
		}
		
		// TODO falta implementar de manera posta
		return 1;
	}

}
