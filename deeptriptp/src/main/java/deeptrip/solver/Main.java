package deeptrip.solver;

import gps.SearchStrategy;
import gps.api.GPSProblem;
import gps.exception.SolutionNotFoundException;
import deeptrip.ai.engine.DeeptripAIEngine;
import deeptrip.ai.enumerators.HeuristicType;
import deeptrip.ai.heuristics.HeuristicOne;
import deeptrip.ai.heuristics.HeuristicThree;
import deeptrip.ai.heuristics.HeuristicTwo;
import deeptrip.ai.problem.DeeptripAIProblem;
import deeptrip.ai.states.DeeptripAIState;
import deeptrip.game.Board;

public class Main {

	private final Board start;
	private final DeeptripAIState startState;
	private final DeeptripAIState endState;
	private GPSProblem problem;
	private final DeeptripAIEngine engine;

	public Main(Integer[][] startBoard) {
		start = new Board(startBoard);
		startState = new DeeptripAIState(start);
		endState = new DeeptripAIState(Main.getEndBoard(start));
		engine = new DeeptripAIEngine();
	}

	public static Board getEndBoard(Board board) {

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

		Integer[][] startBoard = { { 1, 2, 3 }, { 3, 4, 1},
				{ 1, 6, 3}};

		if (args.length != 2) {
			throw new IllegalArgumentException("Incorrect Arguments");
		}
		SearchStrategy searchStrategy = null;
		HeuristicType heuristicType = null;

		if(args[0].compareTo("BFS") == 0){
			searchStrategy = SearchStrategy.BFS;
			System.out.println("Using BFS");
		}else if(args[0].compareTo("DFS") == 0){
			searchStrategy = SearchStrategy.DFS;
			System.out.println("Using DFS");
		}else if(args[0].compareTo("AStar") == 0){
			searchStrategy = SearchStrategy.AStar;
			System.out.println("Using AStar");
		}else if(args[0].compareTo("Greedy") == 0){
			searchStrategy = SearchStrategy.Greedy;
			System.out.println("Using Greedy");
		}else if(args[0].compareTo("IDDFS") == 0){
			searchStrategy = SearchStrategy.IDDFS;
			System.out.println("Using IDDFS");
		}else{
			throw new IllegalArgumentException("Invalid Search Strategy Argument");
		}
		

		if(args[1].compareTo("HOne") == 0){
			heuristicType = HeuristicType.ONE;
			System.out.println("Using Heuristic One");
		}else if(args[1].compareTo("HTwo") == 0){
			heuristicType = HeuristicType.TWO;
			System.out.println("Using Heuristic Two");
		}else if(args[1].compareTo("HThree") == 0){
			heuristicType = HeuristicType.THREE;
			System.out.println("Using Heuristic Three");
		}else{
			throw new IllegalArgumentException("Invalid Heuristic Argument");
		}

		switch (searchStrategy) {
		case IDDFS:
			new Main(startBoard).solveIterativeDeepening(heuristicType);
			break;
		case BFS:
		case DFS:
		case Greedy:
		case AStar:
			new Main(startBoard).solve(searchStrategy, heuristicType);
			break;
		default:
			throw new IllegalArgumentException();
		}

	}

	private void buildProblem(HeuristicType heuristicType) {
		if (heuristicType.compareTo(HeuristicType.ONE) == 0) {
			this.problem = new DeeptripAIProblem(this.startState,
					this.endState, new HeuristicOne());
		} else if (heuristicType.compareTo(HeuristicType.TWO) == 0) {
			this.problem = new DeeptripAIProblem(this.startState,
					this.endState, new HeuristicTwo());
		} else {
			this.problem = new DeeptripAIProblem(this.startState,
					this.endState, new HeuristicThree());
		}
	}

	private void solve(SearchStrategy searchStrategy,
			HeuristicType heuristicType) {
		buildProblem(heuristicType);
		long timeInit = System.currentTimeMillis();
		try {
			engine.engine(problem, searchStrategy);
		} catch (Exception e) {

		}
		System.out.println("Elapsed time: "
				+ (System.currentTimeMillis() - timeInit) + " milliseconds");
	}

	public void solveIterativeDeepening(HeuristicType heuristicType) {
		buildProblem(heuristicType);
		long timeInit = System.currentTimeMillis();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			System.out
					.println("==== Starting Iterative Deepening DFS with a maximum depth of "
							+ i + " ====");
			try {
				engine.engine(problem, SearchStrategy.DFS, i);
				break;
			} catch (SolutionNotFoundException snfe) { /* Do nothing */
			}
		}
		System.out.println("Elapsed time: "
				+ (System.currentTimeMillis() - timeInit) + " milliseconds");
	}

}
