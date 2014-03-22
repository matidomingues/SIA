package deeptrip.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import deeptrip.stategies.Strategy;
import deeptrip.utils.Point;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Board {

	private List<List<Integer>> board;
	private HashSet<Point> modifications;

	public Board(Integer[][] matrix) {
		modifications = new HashSet<Point>();
		board = new LinkedList<List<Integer>>();
		for (int i = 0; i < matrix.length; i++) {
			board.set(i, new LinkedList<Integer>());
			board.get(i).addAll(Arrays.asList(matrix[i]));
		}
	}

	private Board(List<List<Integer>> board) {
		this.board = board;
		modifications = new HashSet<Point>();
	}

	public void applyStrategy(Strategy strategy) {

	}

	private void addModification(final Point point) {
		this.modifications.add(point);
	}

	public HashSet<Point> getModifications() {
		return this.modifications;
	}

	public Board getClonedBoard() {
		List<List<Integer>> newBoard = new LinkedList<List<Integer>>();
		for (int i = 0; i < board.size(); i++) {
			newBoard.set(i, new LinkedList<Integer>());
			newBoard.get(i).addAll(board.get(i));
		}
		return new Board(newBoard);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Board)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		Board b = (Board) obj;
		if (board.equals(b.board)) {
			return true;
		}
		return false;
	}

	public Integer getPoint(Point p) {
		return board.get(p.getX()).get(p.getY());
	}

	public Integer getPointWithCoordinates(int x, int y) {
		return board.get(x).get(y);
	}

	public boolean insideBoundaries(Point p) {
		if (p.getX() < 0 || p.getY() < 0 || p.getX() >= board.size()
				|| p.getY() >= board.get(0).size()) {
			return false;
		}
		return true;
	}

	public void removeColor(Point p) {
		board.get(p.getX()).set(p.getY(), 0);
	}

	public void shiftRow(int row, int shift) {
		Collections.rotate(board.get(row), shift);
		for (int i = 0; i < board.get(row).size(); i++) {
			this.addModification(new Point(row, i));
		}
	}
	
	public void swapColour(Point loc, Point dest){
		board.get(dest.getX()).set(dest.getY(), this.getPoint(loc));
		this.removeColor(loc);
		modifications.add(dest);
	}

	public int getColumnsSize(){
		return board.get(0).size();
	}
	
	public void cleanModifications(){
		modifications = new HashSet<Point>();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.board).build();
	}
    
    public int getRowsSize(){
    	return board.size();
    }
}
