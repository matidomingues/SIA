package deeptrip.stategies;

import deeptrip.game.Board;

public class ShiftRow implements Strategy {

	private final int x;
	private final int shift;
	
	public ShiftRow(final int x, final int shift){
		this.x = x;
		this.shift = shift;
	}
	
	/**
	 * Executes the ShiftRow Strategy
	 * @return a new Board with the modifications
	 */
	public Board execute(final Board board) {
		Board newBoard = board.getClonedBoard();
		newBoard.shiftRow(x, shift);
		while(newBoard.getModifications().size() != 0){
			//System.out.println(newBoard);
			new DropDown().execute(newBoard);
			//System.out.println(newBoard);
			new Consumption().execute(newBoard);
		}
		return newBoard;
	}
	

	
}
