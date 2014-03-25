package deeptrip.ai.heuristics;

import java.util.HashMap;

import gps.api.GPSState;
import deeptrip.ai.states.DowntripAIState;
import deeptrip.game.Board;

public class HeuristicTwo implements Heuristic {

	@Override
	public Integer getHValue(GPSState stateFrom, GPSState goalState) {
		if (!(stateFrom instanceof DowntripAIState))
			return Integer.MAX_VALUE;

		DowntripAIState state = (DowntripAIState) stateFrom;
		Board board = state.getBoard();
		int sumValue = 0;

		HashMap<Integer, Integer> map = board.getColorMap();
		for (Integer key : map.keySet()) {
			
		}

		return sumValue;
	}
	
	private int calculateCoinProblem(int amount){
		int sum = 0;
		while(amount >= 10){
			sum++;
			amount -= 5;
		}
		while(amount >= 8);
		return 1;
	}
	

}
