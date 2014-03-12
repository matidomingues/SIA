package puzzle8;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class moveUpGPSRule implements GPSRule{

	public Integer getCost() {
		return 1;
	}

	public String getName() {
		return "UP";
	}

	public GPSState evalRule(GPSState state) throws NotAppliableException {
		if (state instanceof eightPuzzleGPSState) {
			eightPuzzleGPSState _state = (eightPuzzleGPSState)state;
			if (_state.getEmptySpace().getY() > 0) {
				int[][] newBoard = _state.getBoard();
				int tmp = newBoard[_state.getEmptySpace().getX()][_state.getEmptySpace().getY() -1];
				newBoard[_state.getEmptySpace().getX()][_state.getEmptySpace().getY() - 1] = eightPuzzleGPSState.EMPTY;
				newBoard[_state.getEmptySpace().getX()][_state.getEmptySpace().getY()] = tmp;
				return new eightPuzzleGPSState(newBoard);
			}
		}
		throw new NotAppliableException();
	}

}
