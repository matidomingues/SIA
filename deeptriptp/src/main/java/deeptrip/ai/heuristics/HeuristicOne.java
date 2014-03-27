package deeptrip.ai.heuristics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import deeptrip.ai.states.DeeptripAIState;
import gps.api.GPSState;

public class HeuristicOne implements Heuristic {
	
	
	@Override
	public Integer getHValue(GPSState stateFrom, GPSState goalState) {
		if (!(stateFrom instanceof DeeptripAIState)) {
			throw new IllegalArgumentException();
		}
		
		if (stateFrom.compare(goalState)) {
			return 0;
		}
		DeeptripAIState state = (DeeptripAIState) stateFrom;
		Map<Integer, Integer> map = state.getBoard().getColorMap();
		Integer sumTotalChips=0;
		for(Entry<Integer, Integer> e:map.entrySet()){
			Integer num=e.getValue();
			if(num<3){
				return Integer.MAX_VALUE;
			}
			sumTotalChips+=num;
		}
		
		return sumTotalChips/8;
		
		
	}

}
