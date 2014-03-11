package puzzle8;

import gps.api.GPSState;

public class eightPuzzleGPSState implements GPSState{
	
	public static final int EMPTY=0;
	
	
	private int[][] board;
	
	public eightPuzzleGPSState(int[][]board){
		
		this.board=board;
	}
	
	
	public boolean compare(GPSState state) {
		if(state instanceof GPSState){
			eightPuzzleGPSState eState=(eightPuzzleGPSState)state;
			for(int i=0;i<board.length;i++){
				for(int j=0;j<board[i].length;j++){
					if(board[i][j]!=eState.board[i][j]){
						return false;
					}
				}
			}
			return true;
		}
		else{
			return false;
		}

	}

}
