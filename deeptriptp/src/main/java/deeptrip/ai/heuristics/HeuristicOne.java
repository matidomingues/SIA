package deeptrip.ai.heuristics;

import deeptrip.ai.states.DeeptripAIState;
import deeptrip.game.Board;
import deeptrip.utils.Point;
import gps.api.GPSState;

import java.util.Map;
import java.util.Set;

public class HeuristicOne implements Heuristic {
	
	
	@Override
	public Integer getHValue(GPSState stateFrom, GPSState goalState) {
		if (!(stateFrom instanceof DeeptripAIState)) {
			throw new IllegalArgumentException();
		}
		
		if (stateFrom.compare(goalState)) {
			return 0;
		}
		
		Board board = ((DeeptripAIState)stateFrom).getBoard();
		Map<Integer, Set<Point>> colors = board.getColorMap();
		Integer sumTotalChips=0;
		for (Integer color : colors.keySet()) {
			int num=colors.get(color).size();
			if (num < 3) return Integer.MAX_VALUE;
			
			sumTotalChips+=num;
		}
		
		return sumTotalChips/8;
		
		
	}

}
