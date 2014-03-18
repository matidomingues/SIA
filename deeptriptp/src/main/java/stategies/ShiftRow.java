package stategies;

import java.util.Collections;

import game.Board;

public class ShiftRow implements Strategy {

	private final int x;
	private final int shift;
	
	public ShiftRow(int x, int shift){
		this.x = x;
		this.shift = shift;
	}
	
	public void execute(Board board) {
		Collections.rotate(board.getRow(x), shift);
		new DropDown(x).execute(board);
		
	}

}
