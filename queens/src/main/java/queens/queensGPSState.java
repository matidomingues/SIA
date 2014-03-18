package queens;

import gps.api.GPSState;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class queensGPSState implements GPSState {

	/*
	 * El board tiene un 0 si no hay nada, un 1 si hay una reina y un -1 si
	 * retire una reina de ese punto
	 */
	private int[][] board;
	private List<Point> queens;
	private List<Point> removed;

	public queensGPSState(int[][] matrix) {
		queens = new LinkedList<Point>();
		removed = new LinkedList<Point>();
		board = new int[8][8];
		for (int i = 0; i < matrix.length; i++) {
			for (int w = 0; w < matrix[i].length; w++) {
				if (matrix[i][w] == 1) {
					queens.add(new Point(i, w));
				}
				if(matrix[i][w] == -1){
					removed.add(new Point(i,w));
				}
				board[i][w] = matrix[i][w];
			}
		}
	}

	@Override
	public boolean compare(GPSState state) {
		if (state instanceof GPSState) {
			queensGPSState eState = (queensGPSState) state;
			if (eState.queens.size() == this.queens.size() && eState.removed.size() == this.removed.size()) {
				for (Point p : this.queens) {
					if (!eState.queens.contains(p)) {
						return false;
					}
				}
				for(Point p: this.removed){
					if(!eState.removed.contains(p)){
						return false;
					}
				}
			}else{
				return false;
			}
		}
		return true;
	}

	public int[][] getBoard() {
		return this.board;
	}

	public void addQueen(Point point) {
		queens.add(point);
	}

	public List<Point> getQueens() {
		return this.queens;
	}

	public void removeQueen(Point point) {
		queens.remove(point);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + ((queens == null) ? 0 : queens.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		queensGPSState other = (queensGPSState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (queens == null) {
			if (other.queens != null)
				return false;
		} else if (!queens.equals(other.queens))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "queensGPSState [queens=" + queens + "]";
	}

}
