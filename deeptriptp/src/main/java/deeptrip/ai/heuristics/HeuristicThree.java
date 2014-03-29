package deeptrip.ai.heuristics;

import deeptrip.ai.states.DeeptripAIState;
import deeptrip.game.Board;
import deeptrip.utils.Point;
import gps.api.GPSState;

import java.util.Map;
import java.util.Set;

public class HeuristicThree implements Heuristic {

    @Override
    public Integer getHValue(GPSState stateFrom, GPSState goalState) {
		int result = 0;
		Board board = ((DeeptripAIState)stateFrom).getBoard();
		Map<Integer, Set<Point>> colors = board.getColorMap();

		for (Integer color : colors.keySet()) {
			if (colors.get(color).size() < 3) return Integer.MAX_VALUE;
			int x = 0;
			int y = 0;
			int distance = 0;
			for (Point p : colors.get(color)) {
				x += p.getX();
				y += p.getY();
			}
			Point center = new Point(x / colors.get(color).size(), y / colors.get(color).size());
			for (Point p : colors.get(color)) {
				distance += Math.abs(p.getX() - center.getX()) + Math.abs(p.getY() - center.getY());
			}
			result += distance / colors.get(color).size();
		}
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
