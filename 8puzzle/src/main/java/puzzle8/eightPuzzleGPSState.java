package puzzle8;

import gps.api.GPSState;

public class eightPuzzleGPSState implements GPSState {

	public static final int EMPTY = 0;

	private static final int[][] PRIMES = { {2, 3, 5},
		{7, 11, 13},
		{17, 19, 23}
	};
	private int[][] board;
	private int hash = -1;
	private Point emptySpace;

	public eightPuzzleGPSState(int[][] board) {
		
		this.board = board;
		hashCode();
	}

	public boolean compare(GPSState state) {
		if (state instanceof GPSState) {
			eightPuzzleGPSState eState = (eightPuzzleGPSState) state;
			return this.hashCode()==eState.hashCode();
		} else {
			return false;
		}

	}

	@Override
	public int hashCode() {
		if (hash == -1) {
			hash=0;
			for(int i = 0; i <board.length;i++){
				for(int j=0;j<board[i].length;j++){
					if (board[i][j] == EMPTY) {
						if (emptySpace != null) throw new IllegalStateException();
						this.emptySpace = new Point(i, j);
					}
					hash+=PRIMES[i][j]*board[i][j];
				}
			}
		}
		return hash;
	}
	
	public int[][] getBoard(){
		return this.board;
	}
	
	public Point getEmptySpace() {
		return this.emptySpace;
	}

}
