package deeptrip.ai.heuristics;

import deeptrip.utils.Point;
import gps.api.GPSState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gps.api.GPSState;
import deeptrip.ai.states.DeeptripAIState;
import deeptrip.game.Board;

public class HeuristicTwo implements Heuristic {

	@Override
	public Integer getHValue(GPSState stateFrom, GPSState goalState) {
		if (!(stateFrom instanceof DeeptripAIState))
			return Integer.MAX_VALUE;

		DeeptripAIState state = (DeeptripAIState) stateFrom;
		Board board = state.getBoard();
		int sumValue = 0;

		Map<Integer, Set<Point>> map = board.getColorMap();
		for (Integer key : map.keySet()) {
			if(map.get(key).size() < 3){
				sumValue = Integer.MAX_VALUE;
			}
			sumValue = calculateCoinProblem(map.get(key).size());
		}
		return sumValue;
	}

	private int calculateCoinProblem(int amount) {
		int sum = 0;
		while (amount >= 10) {
			sum++;
			amount -= 5;
		}
		switch (amount) {
		case 5:
			sum++;
			break;
		case 6:
		case 7:
		case 8:
			sum += 2;
			break;
		case 9:
			sum += 3;
			break;
		}
		return sum;
	}

}
