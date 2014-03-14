package queens;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class putQueenGPSRule implements GPSRule{

	@Override
	public Integer getCost() {
		return 0;
	}

	@Override
	public String getName() {
		return "PUT";
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		if (state instanceof queensGPSState) {
			queensGPSState _state = (queensGPSState)state;
			int[][] newMatrix = cloneMatrix(_state.getBoard());
			Point point = getNextQueenAvailablePoint(_state);
			if(point != null){
				newMatrix[point.getX()][point.getY()] = 1;
				return new queensGPSState(newMatrix);
			}
		}
		return null;
	}
	
	private Point getNextQueenAvailablePoint(queensGPSState state){
		int[][] board = state.getBoard();
		for(int i = 0; i< board.length; i++){
			for(int w = 0; w < board[i].length; w++){
				if(board[i][w] == 0){
					if(checkQueenLocationElegibility(board, i, w)){
						return new Point(i,w);
					}
				}
			}
		}
		return null;
	}
	
	private boolean checkQueenLocationElegibility(int[][] board, int x, int y){
		return checkDiagonal(board, x, y) && checkHorinzontal(board, x) && checkVertical(board, y);
	}
	
	private boolean checkHorinzontal(int[][] board, int x){
		for(int i = 0; i< board[x].length; i++){
			if(board[x][i] == 1){
				return false;
			}
		}
		return true;
	}
	
	private boolean checkVertical(int[][] board, int y){
		for(int i = 0; i<board.length; i++){
			if(board[i][y] == 1){
				return false;
			}
		}
		return true;
	}
	
	private boolean checkDiagonal(int[][] board, int x, int y){
		for(int i = 0; i< board.length; i++){
			if(x+i < board.length){
				if(y+i <board[x].length){
					if(board[x+i][y+i] == 1){
						return false;
					}
				}
				if(y-i >= 0){
					if(board[x+i][y-i] == 1){
						return false;
					}
				}
			}
			if(x-i >= 0 ){
				if(y+i < board[x].length){
					if(board[x-i][y+i] == 1){
						return false;
					}
				}
				if(y-i >= 0){
					if(board[x-i][y-i] == 1){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private int[][] cloneMatrix(int[][] matrix){
		int [][] copy = new int[matrix.length][];
		for(int i = 0; i < matrix.length; i++){
		    copy[i] = matrix[i].clone();
		}
		return copy;
	}

}
