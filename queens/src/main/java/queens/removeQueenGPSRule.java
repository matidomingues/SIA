package queens;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class removeQueenGPSRule implements GPSRule{

	@Override
	public Integer getCost() {
		return 0;
	}

	@Override
	public String getName() {
		return "DEL";
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		if (state instanceof queensGPSState) {
			queensGPSState _state = (queensGPSState)state;
			if(_state.getQueens().size() == 0){
				throw new NotAppliableException();
			}
			int[][] newMatrix = cloneMatrix(_state.getBoard());
			Point point = _state.getQueens().get(_state.getQueens().size()-1);
			newMatrix[point.getX()][point.getY()] = -1;
			return new queensGPSState(newMatrix);
		}
		return null;
	}
	
	private int[][] cloneMatrix(int[][] matrix){
		int [][] copy = new int[matrix.length][];
		for(int i = 0; i < matrix.length; i++){
		    copy[i] = matrix[i].clone();
		}
		return copy;
	}

}
