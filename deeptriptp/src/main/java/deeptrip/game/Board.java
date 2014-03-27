package deeptrip.game;

import deeptrip.stategies.Consumption;
import deeptrip.stategies.DropDown;
import deeptrip.utils.Point;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

public class Board {

	private List<List<Integer>> board;
	private Set<Point> modifications;
	private Map<Integer, Integer> colorsCounter;
	private Integer chips;

	private Board(List<List<Integer>> board, Map<Integer, Integer> colorsCounter, Set<Point> modifications, Integer chips) {
		this.board = board;
		this.chips = chips;
		this.colorsCounter = new HashMap<>(colorsCounter);
		this.modifications = modifications;
	}

	public Board(Integer[][] matrix) {
        this(new LinkedList<List<Integer>>(), new HashMap<Integer, Integer>(), new HashSet<Point>(), 0);
        initBoard(matrix);
	}

    private void initBoard(Integer[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			List<Integer> interin = new LinkedList<>();
			board.add(i, interin);
			for (int w = 0; w < matrix[i].length; w++) {
				interin.add(matrix[i][w]);
				if (matrix[i][w] > 0) {
					addColorToCounter(matrix[i][w]);
					incrementChips();
				}
			}
		}
        new DropDown().execute(this);
        for (int i = 0; i < this.board.size(); i++) {
            for (int j = 0; j < this.board.get(0).size(); j++) {
                this.modifications.add(new Point(i, j));
            }
        }
        new Consumption().execute(this);
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


	private void addModification(final Point point) {
		this.modifications.add(point);
	}

	public Set<Point> getModifications() {
		return this.modifications;
	}

	public Board getClonedBoard() {
		List<List<Integer>> newBoard = new LinkedList<>();
		for (int i = 0; i < board.size(); i++) {
			newBoard.add(i, new LinkedList<Integer>());
			newBoard.get(i).addAll(board.get(i));
		}
		return new Board(newBoard, colorsCounter, new HashSet<Point>(), chips);
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
		int aux = this.getPoint(dest);
		board.get(dest.getX()).set(dest.getY(), this.getPoint(loc));
		board.get(loc.getX()).set(loc.getY(), aux);
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
	
	public Map<Integer, Integer> getColorMap(){
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
