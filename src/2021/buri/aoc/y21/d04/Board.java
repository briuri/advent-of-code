package buri.aoc.y21.d04;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model for a bingo board.
 *
 * @author Brian Uri!
 */
public class Board {
	private int[][] _board = new int[SIZE][SIZE];

	public static final int SIZE = 5;
	private static final int MARKED = -1;

	/**
	 * Constructor
	 */
	public Board(List<Integer> list) {
		int x = 0;
		int y = 0;
		for (Integer value : list) {
			getBoard()[x][y] = value;
			x++;
			if (x == SIZE) {
				x = 0;
				y++;
			}
		}
	}

	/**
	 * Returns true if there is a row or column completely marked.
	 */
	public boolean isWin() {
		for (int i = 0; i < SIZE; i++) {
			int rowSum = 0;
			int colSum = 0;
			for (int j = 0; j < SIZE; j++) {
				rowSum += get(i, j);
				colSum += get(j, i);
			}
			if (rowSum == MARKED * SIZE || colSum == MARKED * SIZE) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Returns the sum of unmarked cells.
	 */
	public int getUnmarkedSum() {
		int sum = 0;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (get(x, y) != MARKED) {
					sum += get(x, y);
				}
			}
		}
		return (sum);
	}

	/**
	 * Accessor for a single cell on the board.
	 */
	public void mark(int value) {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (get(x, y) == value) {
					getBoard()[x][y] = MARKED;
				}
			}
		}
	}

	/**
	 * Accessor for a single cell on the board.
	 */
	private int get(int x, int y) {
		return (getBoard()[x][y]);
	}

	/**
	 * Accessor for the underlying 2d array
	 */
	private int[][] getBoard() {
		return (_board);
	}
}