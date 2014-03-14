package puzzle8;

import gps.SearchStrategy;
import gps.api.GPSProblem;
import gps.api.GPSState;

public class init {

	public static void main(String[] args) {
		int[][] startBoard = {{5,4,7},{0,2,1},{3,6,8}};
		int[][] endBoard = {{1,2,3},{8,0,4},{7,6,5}};
		eightPuzzleGPSState startState = new eightPuzzleGPSState(startBoard);
		eightPuzzleGPSState endState = new eightPuzzleGPSState(endBoard);
		GPSProblem problem = new eightPuzzleGPSProblem(startState, endState);
		eightPuzzleGPSEngine engine = new eightPuzzleGPSEngine();
		engine.engine(problem, SearchStrategy.DFS);
		
	}

}
