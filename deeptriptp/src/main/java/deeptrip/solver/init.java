package deeptrip.solver;

import gps.SearchStrategy;
import gps.api.GPSProblem;
import deeptrip.ai.engine.DeeptripAIEngine;
import deeptrip.ai.enumerators.HeuristicType;
import deeptrip.ai.heuristics.Heuristic;
import deeptrip.ai.heuristics.HeuristicOne;
import deeptrip.ai.problem.DeeptripAIProblem;
import deeptrip.ai.states.DowntripAIState;
import deeptrip.game.Board;
import deeptrip.stategies.ShiftRow;

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

		Integer[][] startBoard = { { 1, 0, 1,2 }, { 1, 0, 0,2 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

		Integer[][] endBoard = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		/*
		 * Integer[][] endBoard = new Integer[3][4]; for (int i = 0; i < 3; i++)
		 * { for (int j = 0; j < 4; j++) { endBoard[i][j] = 0; } }
		 */
		new init().solveBFS(startBoard);
//		Board start = new Board(startBoard);
//		// System.out.println(new ShiftRow(1, 1).execute(start));
//		System.out.println("begin the solver");
//		Board end = new Board(endBoard);
//		DowntripAIState startState = new DowntripAIState(start);
//		DowntripAIState endState = new DowntripAIState(end);
//		GPSProblem problem = new DeeptripAIProblem(startState, endState);
//		DeeptripAIEngine engine = new DeeptripAIEngine();
//		engine.engine(problem, SearchStrategy.BFS);

	}

	public void solveBFS(Integer[][] startBoard) {
		Board start = new Board(startBoard);
		DowntripAIState startState = new DowntripAIState(start);
		DowntripAIState endState = new DowntripAIState(init.getendBoard(start));
		GPSProblem problem = new DeeptripAIProblem(startState, endState,new HeuristicOne());
		DeeptripAIEngine engine = new DeeptripAIEngine();
		long timeInit=System.currentTimeMillis();
		engine.engine(problem, SearchStrategy.BFS);
		System.out.println("Elapsed time:"+ (System.currentTimeMillis()-timeInit)+" milliseconds");
	}

	public void solveDFS(Integer[][] startBoard) {
		Board start = new Board(startBoard);
		DowntripAIState startState = new DowntripAIState(start);
		DowntripAIState endState = new DowntripAIState(init.getendBoard(start));
		GPSProblem problem = new DeeptripAIProblem(startState, endState, new HeuristicOne());
		long timeInit=System.currentTimeMillis();
		DeeptripAIEngine engine = new DeeptripAIEngine();
		engine.engine(problem, SearchStrategy.DFS);
		System.out.println("Elapsed time:"+ (System.currentTimeMillis()-timeInit)+" milliseconds");
	}

	public void solveIterativeProfundization(Integer[][] startBoard) {

	}
	
	public void solveAStar(Integer[][] startBoard, HeuristicType heuristicType){
		Board start = new Board(startBoard);
		DowntripAIState startState = new DowntripAIState(start);
		DowntripAIState endState = new DowntripAIState(init.getendBoard(start));
		GPSProblem problem = new DeeptripAIProblem(startState, endState, new HeuristicOne());
		long timeInit=System.currentTimeMillis();
		DeeptripAIEngine engine = new DeeptripAIEngine();
		engine.engine(problem, SearchStrategy.DFS);
		System.out.println("Elapsed time:"+ (System.currentTimeMillis()-timeInit)+" milliseconds");
		
	}

}
