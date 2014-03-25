package deeptrip.solver;

import deeptrip.ai.engine.DeeptripAIEngine;
import deeptrip.ai.problem.DeeptripAIProblem;
import deeptrip.ai.states.DowntripAIState;
import deeptrip.game.Board;
import gps.SearchStrategy;
import gps.api.GPSProblem;

public class init {

	public static Board getendBoard(Board board) {

		int rows = board.getRowsSize();
		int columns = board.getColumnsSize();
		Integer[][] end = new Integer[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				end[i][j] = 0;
			}
		}
		return new Board(end);
	}

	public static void main(String[] args) {

		// Integer[][] matrix = { { 1, 1, 0, 3 }, { 3, 3, 0, 1 } };
		// Board board = new Board(matrix);
		// System.out.println(board);
		// System.out.println(new ShiftRow(1, 1).execute(board));

		Integer[][] startBoard = { { 1, 0, 1, 0 }, { 1, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

		Integer[][] endBoard = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		/*
		 * Integer[][] endBoard = new Integer[3][4]; for (int i = 0; i < 3; i++)
		 * { for (int j = 0; j < 4; j++) { endBoard[i][j] = 0; } }
		 */
		Board start = new Board(startBoard);
		// System.out.println(new ShiftRow(1, 1).execute(start));
		System.out.println("begin the solver");
		Board end = new Board(endBoard);
        SearchStrategy searchStrategy = SearchStrategy.BFS;
		DowntripAIState startState = new DowntripAIState(start);
		DowntripAIState endState = new DowntripAIState(end);
		GPSProblem problem = new DeeptripAIProblem(startState, endState);
		DeeptripAIEngine engine = new DeeptripAIEngine();

        switch (searchStrategy) {
            case IDDFS:
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    engine.engine(problem, SearchStrategy.DFS, i);
                }
            case BFS:
            case DFS:
            case Greedy:
            case AStar:
            default:
                engine.engine(problem, searchStrategy);
        }

	}

	public void solveBFS(Integer[][] startBoard) {
		Board start = new Board(startBoard);
		DowntripAIState startState = new DowntripAIState(start);
		DowntripAIState endState = new DowntripAIState(init.getendBoard(start));
		GPSProblem problem = new DeeptripAIProblem(startState, endState);
		DeeptripAIEngine engine = new DeeptripAIEngine();
		engine.engine(problem, SearchStrategy.BFS);
	}

	public void solveDFS(Integer[][] startBoard) {
		Board start = new Board(startBoard);
		DowntripAIState startState = new DowntripAIState(start);
		DowntripAIState endState = new DowntripAIState(init.getendBoard(start));
		GPSProblem problem = new DeeptripAIProblem(startState, endState);
		DeeptripAIEngine engine = new DeeptripAIEngine();
		engine.engine(problem, SearchStrategy.DFS);
	}

	public void solveIterativeProfundization(Integer[][] startBoard) {

	}

}
