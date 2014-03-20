package deeptrip.stategies;

import java.util.Collections;

import deeptrip.game.Board;

public class ShiftRow implements Strategy {

	private final int x;
	private final int shift;
	
	public ShiftRow(final int x, final int shift){
		this.x = x;
		this.shift = shift;
	}
	
	public Board execute(final Board board) {
		Board newBoard = board.getClonedBoard();
		Collections.rotate(board.getRow(x), shift);
		new DropDown(x).execute(board);
		return newBoard;
	}
	
}
