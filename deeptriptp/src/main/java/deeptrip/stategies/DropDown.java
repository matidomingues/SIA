package deeptrip.stategies;

import java.util.List;

import deeptrip.game.Board;
import deeptrip.utils.Point;

public class DropDown implements Strategy {

	private void lookUp(Board board, Point p) {
		Point finalPoint = getUpNotEmpty(board, p);
		if(finalPoint != null){
			board.swapColour(finalPoint, p);
			if(board.getPointWithCoordinates(p.getX(), p.getY()+1) == 0){
				lookUp(board, new Point(p.getX(),p.getY()+1));
			}
		}
	}
	
	private Point getUpNotEmpty(Board board, Point p){
		int x = p.getX();
		int y = p.getY();
		for(; y < board.getColumnsSize() ; y++){
			if(board.getPointWithCoordinates(x, y) != 0){
				return(new Point(x,y));
			}
		}
		return null;
	}

	private void lookDown(Board board, Point p) {
		Point finalPoint = getDownEmpty(board, p);
		if(finalPoint != null){
			board.swapColour(p, finalPoint);
			if(board.getPointWithCoordinates(finalPoint.getX(), finalPoint.getY()+1) == 0){
				lookUp(board, new Point(finalPoint.getX(), finalPoint.getY()+1));
			}
		}
	}

	private Point getDownEmpty(Board board, Point p) {
		int x = p.getX();
		int y = p.getY();
		for (; y >= 0; y--) {
			if (board.getPointWithCoordinates(x, y) != 0) {
				return new Point(x, y + 1);
			}
		}
		if(y == -1 && p.getY() != 0){
			return new Point(x,y+1);
		}
		return null;
	}

	public Board execute(final Board board) {
		for(Point p : board.getModifications()){
			if(board.getPoint(p) == 0){
				Point down = getDownEmpty(board, p);
				if(down != null){
					lookUp(board, down);
				}else{
					lookUp(board, p);
				}
			}else{
				lookDown(board, p);
			}
		}
		return board;
	}
}
