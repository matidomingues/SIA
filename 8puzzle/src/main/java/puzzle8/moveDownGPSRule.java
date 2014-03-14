package puzzle8;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class moveDownGPSRule implements GPSRule{

	public Integer getCost() {
		return 1;
	}

	public String getName() {
		return "DOWN";
	}

	public GPSState evalRule(GPSState state) throws NotAppliableException {
		if (state instanceof eightPuzzleGPSState) {
			eightPuzzleGPSState _state = (eightPuzzleGPSState)state;
			if (_state.getEmptySpace().getY() < _state.getBoard()[0].length -1) {
				int[][] newBoard = cloneMatrix(_state.getBoard());
				newBoard[_state.getEmptySpace().getX()][_state.getEmptySpace().getY()] = newBoard[_state.getEmptySpace().getX()][_state.getEmptySpace().getY() + 1] ;
                newBoard[_state.getEmptySpace().getX()][_state.getEmptySpace().getY() + 1] = eightPuzzleGPSState.EMPTY;
                return new eightPuzzleGPSState(newBoard);
			}
		}
		throw new NotAppliableException();
	}
	
	private int[][] cloneMatrix(int[][] matrix){
		int [][] copy = new int[matrix.length][];
		for(int i = 0; i < matrix.length; i++){
		    copy[i] = matrix[i].clone();
		}
		return copy;
	}

}
