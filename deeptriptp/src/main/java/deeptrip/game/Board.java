package deeptrip.game;

import java.util.Arrays;
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
	
	private Board(List<List<Integer>> board){
		this.board = board;
		modifications = new HashSet<Point>();
	}

	public List<Integer> getRow(int x) {
		if (x >= board.size() || x < 0) {
			throw new IllegalArgumentException();
		}
		return board.get(x);

	}

	public void applyStrategy(Strategy strategy) {

	}

	public void addModification(final Point point){
		this.modifications.add(point);
	}
	
	public HashSet<Point> getModifications(){
		return this.modifications;
	}
	
	public Board getClonedBoard(){
		List<List<Integer>> newBoard = new LinkedList<List<Integer>>();
		for (int i = 0; i < board.size(); i++) {
			newBoard.set(i, new LinkedList<Integer>());
			newBoard.get(i).addAll(board.get(i));
		}
		return new Board(newBoard);
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Board)) { return false; }
        if (obj == this) { return true; }
        Board b = (Board)obj;
        if (board.equals(b.board)) { return true; }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.board).build();
    }
}
