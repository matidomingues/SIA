package game;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import stategies.Strategy;

public class Board {

	private List<List<Integer>> board;

	public Board(Integer[][] matrix) {
		board = new LinkedList<List<Integer>>();
		for (int i = 0; i < matrix.length; i++) {
			board.set(i, new LinkedList<Integer>());
			board.get(i).addAll(Arrays.asList(matrix[i]));
		}
	}

	public List<Integer> getRow(int x) {
		if (x > board.size() || x < 0) {
			throw new IllegalArgumentException();
		}
		return board.get(x);
	}

	public void applyStrategy(Strategy strategy) {

	}

	public void calculateMovements(int x) {

	}
}
