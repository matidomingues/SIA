package deeptrip.stategies;

import deeptrip.game.Board;
import org.apache.log4j.Logger;

public class ShiftRow implements Strategy {

	private static final Logger logger = Logger.getLogger(ShiftRow.class);

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
			if (logger.isTraceEnabled()) logger.trace(newBoard);
			new DropDown().execute(newBoard);
			if (logger.isTraceEnabled()) logger.trace(newBoard);
			new Consumption().execute(newBoard);
		}
		return newBoard;
	}
	

	
}
