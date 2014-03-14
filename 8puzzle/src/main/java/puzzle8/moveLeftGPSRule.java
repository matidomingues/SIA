package puzzle8;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class moveLeftGPSRule implements GPSRule{

	public Integer getCost() {
		return 1;
	}

	public String getName() {
		return "LEFT";
	}

	public GPSState evalRule(GPSState state) throws NotAppliableException {
        if (state instanceof eightPuzzleGPSState) {
            eightPuzzleGPSState _state = (eightPuzzleGPSState)state;
            if (_state.getEmptySpace().getX() > 0) {
                int[][] newBoard = _state.getBoard();
                newBoard[_state.getEmptySpace().getX()][_state.getEmptySpace().getY()] = newBoard[_state.getEmptySpace().getX() -1][_state.getEmptySpace().getY()];
                newBoard[_state.getEmptySpace().getX() -1][_state.getEmptySpace().getY()] = eightPuzzleGPSState.EMPTY;
                return new eightPuzzleGPSState(newBoard);
            }
        }
        throw new NotAppliableException();
	}

}
