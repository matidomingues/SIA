package deeptrip.solver;

import deeptrip.game.Board;
import deeptrip.stategies.ShiftRow;

public class init {

	public static void main(String[] args) {
		Integer[][] matrix = { { 1, 1, 0, 3 }, { 3, 3, 0, 1 } };
		Board board = new Board(matrix);
		System.out.println(board);
		System.out.println(new ShiftRow(1, 1).execute(board));
	}

}
