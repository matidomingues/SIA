package deeptrip.stategies;

import java.util.List;

import deeptrip.game.Board;

public class DropDown implements Strategy{

	private final int x;
	
	public DropDown(final int x) {
		this.x = x;
	}

	public void execute(final Board board) {
		List<Integer> row = board.getRow(x);
		for(int i = 0; i< row.size(); i++){
			if(row.get(i) == 0){
				
			}
		}
	}

}
