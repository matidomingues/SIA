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
					if(checkQueenLocationElegibility(state, i, w)){
						return new Point(i,w);
					}
				}
			}
		}
		return null;
	}
	
	/* 1 - 4
	 * 1 - 2
	 * 2 - 3
	000000000
	000000000
	000000000
	001000000
	000000000
	000000000
	000000000
	000000000
	000000000
	
	100000000
	001000000
	010000000
	000001000
	000000010
	000100000
	000000000
	000010000
	000000000
	*/
	
	private boolean checkQueenLocationElegibility(queensGPSState state, int x, int y){
		for(Point p: state.getQueens()){
			if(x == p.getX() || y == p.getY()){
				return false;
			}else if(Math.abs(x-p.getX()) == Math.abs(y-p.getY())){
				return false;
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
