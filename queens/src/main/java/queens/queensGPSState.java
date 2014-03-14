package queens;

import gps.api.GPSState;

public class queensGPSState implements GPSState{

	/* El board tiene un 0 si no hay nada, un 1 si hay una reina
	 * y un -1 si retire una reina de ese punto
	 */
	private int[][] board;
	
	public queensGPSState(int[][] matrix){
		for(int i = 0; i<matrix.length; i++){
			for(int w = 0; w<matrix[i].length; w++){
				board[i][w] = matrix[i][w];
			}
		}	
	}
	
	@Override
	public boolean compare(GPSState state) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int[][] getBoard(){
		return this.board;
	}

}
