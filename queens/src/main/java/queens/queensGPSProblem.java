package queens;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.util.LinkedList;
import java.util.List;

public class queensGPSProblem implements GPSProblem{

	queensGPSState finalState;
	
	public queensGPSProblem(){
		int[][] board = {{0,0,0,1,0,0,0,0},
				 {0,0,0,0,0,0,1,0},
				 {0,0,1,0,0,0,0,0},
				 {0,0,0,0,0,0,0,1},
				 {0,1,0,0,0,0,0,0},
				 {0,0,0,0,1,0,0,0},
				 {1,0,0,0,0,0,0,0},
				 {0,0,0,0,0,1,0,0}};

		finalState = new queensGPSState(board);
	}

	@Override
	public GPSState getInitState() {
		int[][] board = new int[8][8];
		for(int i = 0; i<8; i++){
			for(int w = 0; w<8; w++){
				board[i][w] = 0;
			}
		}
		return new queensGPSState(board);
	}

	@Override
	public GPSState getGoalState() {
		return this.finalState;
	}

	@Override
	public List<GPSRule> getRules() {
		List<GPSRule> rules = new LinkedList<GPSRule>();
		rules.add(new putQueenGPSRule());
		rules.add(new removeQueenGPSRule());
		return rules;
	}

	@Override
	public Integer getHValue(GPSState state) {
		return 0;
	}

}
