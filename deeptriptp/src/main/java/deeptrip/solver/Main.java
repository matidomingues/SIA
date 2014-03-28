package deeptrip.solver;

import deeptrip.ai.engine.DeeptripAIEngine;
import deeptrip.ai.heuristics.HeuristicOne;
import deeptrip.ai.heuristics.HeuristicTwo;
import deeptrip.ai.problem.DeeptripAIProblem;
import deeptrip.ai.states.DeeptripAIState;
import deeptrip.game.Board;
import gps.SearchStrategy;
import gps.api.GPSProblem;
import gps.exception.SolutionNotFoundException;

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
        problem = new DeeptripAIProblem(startState, endState, new HeuristicTwo());
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

		Integer[][] startBoard =
//              { { 1, 0, 1, 1 }, { 2, 2, 0, 2 },
//				{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
                { { 1, 2, 3, 4, 2 },
                  { 6, 1, 6, 2, 3 },
                  { 1, 3, 1, 3, 6 },
                  { 4, 1, 6, 4, 3 },
                  { 1, 6, 3, 2, 4 },
                  { 5, 1, 5, 1, 2 },
                  { 1, 5, 3, 4, 1 },
                };

        SearchStrategy searchStrategy = SearchStrategy.IDDFS;

        switch (searchStrategy) {
            case IDDFS:
                new Main(startBoard).solveIterativeDeepening();
                break;
            case BFS:
            case DFS:
            case Greedy:
            case AStar:
                new Main(startBoard).solve(searchStrategy);
                break;
            default:
                throw new IllegalArgumentException();
        }

	}

    private void solve(SearchStrategy searchStrategy) {
        long timeInit = System.currentTimeMillis();
        engine.engine(problem, searchStrategy);
        System.out.println("Elapsed time: " + (System.currentTimeMillis()-timeInit) + " milliseconds");
    }

	public void solveIterativeDeepening() {
        long timeInit = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println("==== Starting Iterative Deepening DFS with a maximum depth of " + i + " ====");
            try {
                engine.engine(problem, SearchStrategy.DFS, i);
                break;
            } catch (SolutionNotFoundException snfe) { /* Do nothing */ }
        }
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeInit) + " milliseconds");
	}

}
