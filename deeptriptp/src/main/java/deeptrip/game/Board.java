package deeptrip.game;

import deeptrip.stategies.Consumption;
import deeptrip.stategies.DropDown;
import deeptrip.utils.Point;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

public class Board {

	private List<List<Integer>> board;
	private Set<Point> modifications;
	private Map<Integer, Set<Point>> colorsCounter;
	private Integer chips;

	private Board(List<List<Integer>> board, Map<Integer, Set<Point>> colorsCounter, Set<Point> modifications, Integer chips) {
		this.board = board;
		this.chips = chips;
		this.colorsCounter = new HashMap<>();
		for(Integer color : colorsCounter.keySet()) {
			this.colorsCounter.put(color, new HashSet<>(colorsCounter.get(color)));
		}
		this.modifications = modifications;
	}

	public Board(List<List<Integer>> board) {
        this(board, new HashMap<Integer, Set<Point>>(), new HashSet<Point>(), 0);
        init();
	}

    private void init() {
        new DropDown().execute(this);
        for (int i = 0; i < this.board.size(); i++) {
            for (int j = 0; j < this.board.get(0).size(); j++) {
				Point p = Point.of(i, j);
                this.modifications.add(p);
				if (colorsCounter.get(this.getPoint(p)) == null) {
					colorsCounter.put(this.getPoint(p), new HashSet<Point>());
				}
				colorsCounter.get(this.getPoint(p)).add(p);
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

	private void addColorToCounter(Point point, Integer color) {
		if (this.colorsCounter.get(color) == null) {
            this.colorsCounter.put(color, new HashSet<Point>());
        }
        this.colorsCounter.get(color).add(point);
	}

	private void removeColorToCounter(Point point, Integer color) {
        if (colorsCounter.get(color) != null) {
            colorsCounter.get(color).remove(point);
			if (colorsCounter.get(color).size() == 0) {
				colorsCounter.remove(color);
			}
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
		removeColorToCounter(p, this.getPoint(p));
		board.get(p.getX()).set(p.getY(), 0);
		modifications.add(p);
		decrementChips();
	}

	public void shiftRow(int row, int shift) {
		for (int col = 0; col < board.get(row).size(); col++) {
			Point p = Point.of(row, col);
			removeColorToCounter(p, getPoint(p));
		}
		Collections.rotate(board.get(row), shift);
		for (int col = 0; col < board.get(row).size(); col++) {
			Point p = Point.of(row, col);
			this.addModification(p);
			addColorToCounter(p, getPoint(p));
		}
	}

	public void swapColor(Point loc, Point dest) {
		if (!insideBoundaries(loc) || !insideBoundaries(dest)) {
			throw new IllegalArgumentException();
		}
		int locColor = this.getPoint(dest);
		int destColor = this.getPoint(loc);
		if(locColor != 0){
			addColorToCounter(loc, locColor);
			removeColorToCounter(dest, locColor);
		}
		removeColorToCounter(loc, destColor);
		addColorToCounter(dest, destColor);
		board.get(dest.getX()).set(dest.getY(), destColor);
		board.get(loc.getX()).set(loc.getY(), locColor);
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
		return this.colorsCounter.get(color).size();
	}
	
	public Map<Integer, Set<Point>> getColorMap(){
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
