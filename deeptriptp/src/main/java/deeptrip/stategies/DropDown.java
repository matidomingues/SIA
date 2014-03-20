package deeptrip.stategies;

import java.util.List;

import deeptrip.game.Board;
import deeptrip.utils.Point;

public class DropDown implements Strategy{

	private final int x;
	
	public DropDown(final int x) {
		this.x = x;
	}

	public Board execute(final Board board) {
		boolean dropUpper = false;
		boolean dropLower = false;
		if(x == 0){
			return board;
		}
		List<Integer> lowerRow = board.getRow(x-1);
		List<Integer> upperRow = board.getRow(x+1);
		List<Integer> row = board.getRow(x);
		for(int i = 0; i< row.size(); i++){
			if(row.get(i) != 0 && lowerRow.get(i) == 0){
				lowerRow.set(i, row.get(i));
				row.set(i, 0);
				dropLower = true;
				board.addModification(new Point(x,i));
			}
			if(row.get(i) == 0 && upperRow.get(i) != 0){
				dropUpper = true;
			}
		}
		
		if(dropLower){
			new DropDown(x-1).execute(board);
		}
		if(dropUpper){
			new DropDown(x+1).execute(board);
		}
		return board;
	}

}
