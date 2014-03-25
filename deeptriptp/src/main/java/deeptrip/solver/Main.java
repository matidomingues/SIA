package deeptrip.solver;

import deeptrip.ai.engine.DeeptripAIEngine;
import deeptrip.ai.heuristics.HeuristicOne;
import deeptrip.ai.problem.DeeptripAIProblem;
import deeptrip.ai.states.DeeptripAIState;
import deeptrip.game.Board;
import gps.SearchStrategy;
import gps.api.GPSProblem;

public class Main {

    private final Board start;
    private final DeeptripAIState startState;
    private final DeeptripAIState endState;
    private final GPSProblem problem;
    private final DeeptripAIEngine engine;

    public Main(Integer[][] startBoard) {
        start = new Board(startBoard);
        startState = new DeeptripAIState(start);
        endState = new DeeptripAIState(Main.getEndBoard(start));
        problem = new DeeptripAIProblem(startState, endState, new HeuristicOne());
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

		Integer[][] startBoard = { { 1, 0, 1,2 }, { 1, 0, 0,2 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

		Integer[][] endBoard = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        SearchStrategy searchStrategy = SearchStrategy.IDDFS;

        switch (searchStrategy) {
            case IDDFS:
                new Main(startBoard).solveIterativeProfundization();
                break;
            case BFS:
                new Main(startBoard).solveBFS();
                break;
            case DFS:
                new Main(startBoard).solveDFS();
                break;
            case Greedy:
            case AStar:
                new Main(startBoard).solveAStar();
            default:
        }

	}

    private void solveAStar() {
        long timeInit=System.currentTimeMillis();
        engine.engine(problem, SearchStrategy.DFS);
        System.out.println("Elapsed time:"+ (System.currentTimeMillis()-timeInit)+" milliseconds");
    }

    public void solveBFS() {
		long timeInit=System.currentTimeMillis();
		engine.engine(problem, SearchStrategy.BFS);
		System.out.println("Elapsed time:" + (System.currentTimeMillis() - timeInit) + " milliseconds");
	}

	public void solveDFS() {
		long timeInit=System.currentTimeMillis();
		engine.engine(problem, SearchStrategy.DFS);
		System.out.println("Elapsed time:" + (System.currentTimeMillis() - timeInit) + " milliseconds");
	}

	public void solveIterativeProfundization() {
        long timeInit = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            engine.engine(problem, SearchStrategy.DFS, i);
        }
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeInit) + " milliseconds");

	}

}
