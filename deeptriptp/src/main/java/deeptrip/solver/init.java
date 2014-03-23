package deeptrip.solver;

import gps.SearchStrategy;
import gps.api.GPSProblem;
import deeptrip.ai.engine.DeeptripAIEngine;
import deeptrip.ai.problem.DeeptripAIProblem;
import deeptrip.ai.states.DowntripAIState;
import deeptrip.game.Board;
import deeptrip.stategies.ShiftRow;

public class init {

	public static void main(String[] args) {

//		Integer[][] matrix = { { 1, 1, 0, 3 }, { 3, 3, 0, 1 } };
//		Board board = new Board(matrix);
//		System.out.println(board);
//		System.out.println(new ShiftRow(1, 1).execute(board));

		Integer[][] startBoard = { { 1, 0, 1, 0 },
				{ 1, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 } };

		Integer[][] endBoard={{ 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }};
		/*Integer[][] endBoard = new Integer[3][4];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				endBoard[i][j] = 0;
			}
		}*/
		Board start = new Board(startBoard);
//		System.out.println(new ShiftRow(1, 1).execute(start));
		System.out.println("begin the solver");
		Board end = new Board(endBoard);
		DowntripAIState startState = new DowntripAIState(start);
		DowntripAIState endState = new DowntripAIState(end);
		GPSProblem problem = new DeeptripAIProblem(startState, endState);
		DeeptripAIEngine engine = new DeeptripAIEngine();
		engine.engine(problem, SearchStrategy.BFS);

	}

}
