package deeptrip.stategies;

import java.util.Collections;

import deeptrip.game.Board;
import deeptrip.utils.Point;

public class ShiftRow implements Strategy {

	private final int x;
	private final int shift;
	
	public ShiftRow(final int x, final int shift){
		this.x = x;
		this.shift = shift;
	}
	
	public Board execute(final Board board) {
		Board newBoard = board.getClonedBoard();
		newBoard.shiftRow(x, shift);
		while(newBoard.getModifications().size() != 0){
			new DropDown().execute(newBoard);
			new Consumption().execute(newBoard);
		}
		return newBoard;
	}
	

	
}
