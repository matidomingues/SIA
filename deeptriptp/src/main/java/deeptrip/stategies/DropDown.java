package deeptrip.stategies;

import java.util.HashSet;
import java.util.List;

import deeptrip.game.Board;
import deeptrip.utils.Point;

public class DropDown implements Strategy {

	private void lookUp(Board board, Point p) {
		Point finalPoint = getUpNotEmpty(board, p);
		if(finalPoint != null){
			board.swapColour(finalPoint, p);
			if(board.getPointWithCoordinates(p.getX()+1, p.getY()) == 0){
				lookUp(board, new Point(p.getX()+1,p.getY()));
			}
		}
	}
	
	private Point getUpNotEmpty(Board board, Point p){
		int x = p.getX();
		int y = p.getY();
		for(; x < board.getRowsSize(); x++){
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
			if(board.getPointWithCoordinates(finalPoint.getX()+1, finalPoint.getY()) == 0){
				lookUp(board, new Point(finalPoint.getX()+1, finalPoint.getY()));
			}
		}
	}

	private Point getDownEmpty(Board board, Point p) {
		int x = p.getX();
		int y = p.getY();
		int answer = -1;
		for (; x >= 0; x--) {
			if (board.getPointWithCoordinates(x, y) == 0) {
				answer = x;
			}else{
				break;
			}
		}
		if(answer == -1){
			return null;
		}
		return new Point(answer,y);
	}

	public Board execute(final Board board) {
		HashSet<Point> modifications = new HashSet<Point>();
		modifications.addAll(board.getModifications());
		for(Point p : modifications){
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
