package deeptrip.game;

import deeptrip.stategies.Strategy;
import deeptrip.utils.Point;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

public class Board {

	private List<List<Integer>> board;
	private HashSet<Point> modifications;
	private HashMap<Integer, Integer> colorsCounter;
	private Integer chips;

	public Board(Integer[][] matrix) {
		modifications = new HashSet<>();
		colorsCounter = new HashMap<>();
		board = new LinkedList<>();
		chips = 0;
		for (int i = 0; i < matrix.length; i++) {
			List<Integer> interin = new LinkedList<>();
			board.add(i, interin);
			for (int w = 0; w < matrix[i].length; w++) {
				interin.add(matrix[i][w]);
				incrementChips();
			}
		}
	}

	private void incrementChips(){
		chips++;
	}
	
	private void decrementChips(){
		chips--;
	}
	
	private void addColorToCounter(Integer color) {
		if (colorsCounter.containsKey(color)) {
			colorsCounter.put(color, colorsCounter.get(color) + 1);
		} else {
			colorsCounter.put(color, 1);
		}
	}

	private void removeColorToCounter(Integer color) {
		if (colorsCounter.containsKey(color) && colorsCounter.get(color) > 0) {
			colorsCounter.put(color, colorsCounter.get(color) - 1);
		} else {
			throw new IllegalArgumentException("Incorrect color");
		}
	}

	private Board(List<List<Integer>> board, HashMap<Integer, Integer> counter) {
		this.board = board;
		this.colorsCounter = new HashMap<>(counter);
		modifications = new HashSet<>();
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
		List<List<Integer>> newBoard = new LinkedList<>();
		for (int i = 0; i < board.size(); i++) {
			newBoard.add(i, new LinkedList<Integer>());
			newBoard.get(i).addAll(board.get(i));
		}
		return new Board(newBoard, colorsCounter);
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
		if (!insideBoundaries(p)) {
			throw new IllegalArgumentException();
		}
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
		if (!insideBoundaries(p)) {
			throw new IllegalArgumentException();
		}
		removeColorToCounter(this.getPoint(p));
		board.get(p.getX()).set(p.getY(), 0);
		modifications.add(p);
		decrementChips();
	}

	public void shiftRow(int row, int shift) {
		Collections.rotate(board.get(row), shift);
		for (int i = 0; i < board.get(row).size(); i++) {
			this.addModification(new Point(row, i));
		}
	}

	public void swapColor(Point loc, Point dest) {
		if (!insideBoundaries(loc) || !insideBoundaries(dest)) {
			throw new IllegalArgumentException();
		}
		board.get(dest.getX()).set(dest.getY(), this.getPoint(loc));
		this.removeColor(loc);
		modifications.add(dest);
	}

	public int getColumnsSize() {
		return board.get(0).size();
	}

	public void cleanModifications() {
		modifications = new HashSet<>();
	}

	public int getRowsSize() {
		return board.size();
	}
	
	public int getChipsAmount(){
		return this.chips;
	}
	
	public int getColorAmount(Integer color){
		return this.colorsCounter.get(color);
	}
	
	public HashMap<Integer, Integer> getColorMap(){
		return this.colorsCounter;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.board).build();
	}

	@Override
	public String toString() {
		return "Board [board=" + board + "]";
	}

}
