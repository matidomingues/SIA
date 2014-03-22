package deeptrip.stategies;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import deeptrip.game.Board;
import deeptrip.utils.Point;

public class Consumption implements Strategy{

	Set<Point> evaluated = new HashSet<Point>();
	
	
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
		List<Point> points = new LinkedList<Point>();
		if(!board.insideBoundaries(p) || board.getPoint(p) != color || evaluated.contains(p)){
			return points;
		}
		evaluated.add(p);
		points.addAll(findColor(board,new Point(p.getX(), p.getY()-1), color));
		points.addAll(findColor(board, new Point(p.getX(), p.getY()+1), color));
		points.addAll(findColor(board, new Point(p.getX()-1, p.getY()), color));
		points.addAll(findColor(board, new Point(p.getX()+1, p.getY()), color));
		
		points.add(p);
		
		
		return points;
	}
		
		
		

}
