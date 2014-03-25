package deeptrip.stategies;

import deeptrip.game.Board;
import deeptrip.utils.Point;

import java.util.*;

public class Consumption implements Strategy{

	Set<Point> evaluated = new HashSet<>();
	
	
	/*
	 * (non-Javadoc)
	 * @see deeptrip.stategies.Strategy#execute(deeptrip.game.Board)
	 * Must clean modifications since it makes no sense to check them again anywhere
	 */
	public Board execute(Board board) {
		HashSet<Point> modifications = board.getModifications();
		board.cleanModifications();
		for(Point p : modifications){
			if(!evaluated.contains(p) && board.getPoint(p) != 0){
				evaluatePoint(board, p);
			}
		}
		return board;
	}
	
	private void evaluatePoint(Board board, Point p){
		List<Point> points = findColor(board, p, board.getPoint(p));
		if(points.size() >= 3){
			consumePoints(board, points);
		}
	}
	
	private void consumePoints(Board board, List<Point> points){
		for(Point p: points){
			board.removeColor(p);
		}
	}
	

	
	private List<Point> findColor(Board board, Point p, int color){
		
		if(!board.insideBoundaries(p) || board.getPoint(p) != color || evaluated.contains(p)){
			return Collections.EMPTY_LIST;
		}
		evaluated.add(p);
		List<Point> points = new LinkedList<>();
		points.addAll(findColor(board,new Point(p.getX(), p.getY()-1), color));
		points.addAll(findColor(board, new Point(p.getX(), p.getY()+1), color));
		points.addAll(findColor(board, new Point(p.getX()-1, p.getY()), color));
		points.addAll(findColor(board, new Point(p.getX()+1, p.getY()), color));
		
		points.add(p);
		
		
		return points;
	}
		
		
		

}
