package puzzle8;

import java.util.Arrays;

import gps.api.GPSState;

public class eightPuzzleGPSState implements GPSState {

	public static final int EMPTY = 0;

	private static final int[][] PRIMES = { {1, 10, 100},
		{1000, 10000, 100000},
		{1000000, 10000000, 100000000}
	};
	private final int[][] board;
	private int hash = -1;
	private Point emptySpace;

	public eightPuzzleGPSState(int[][] board) {
		this.board = board;
        init();
	}

    public boolean compare(GPSState state) {
		if (state instanceof GPSState) {
			eightPuzzleGPSState eState = (eightPuzzleGPSState) state;
			return this.hashCode() == eState.hashCode();
		} else {
			return false;
		}

	}

    private void init() {
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

	@Override
	public int hashCode() {
		if (hash == -1) {
			init();
		}
		return hash;
	}

	public int[][] getBoard(){
		return this.board;
	}
	
	public Point getEmptySpace() {
		return this.emptySpace;
	}

	@Override
	public String toString() {
		return "[["+board[0][0] + "," + board[0][1] + "," + board[0][2] + "]"+
				"["+board[1][0] + "," + board[1][1] + "," + board[1][2] + "]"+
				"["+board[2][0] + "," + board[2][1] + "," + board[2][2] + "]]";
	}
	
	

}
