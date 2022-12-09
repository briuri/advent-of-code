package buri.aoc.y21.d11;

import java.util.List;

import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for a grid of octopuses.
 *
 * @author Brian Uri!
 */
public class Grid extends IntGrid {
	private long _flashCount = 0;

	/**
	 * Constructor
	 */
	public Grid(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, Character.getNumericValue(input.get(y).charAt(x)));
			}
		}
	}

	/**
	 * Takes one turn incrementing energy levels and flashing octopuses.
	 */
	public void nextTurn() {
		// Track which octopus has flashed on this turn so it doesn't flash again.
		boolean[][] flashedThisTurn = new boolean[this.getWidth()][this.getHeight()];
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				flashedThisTurn[x][y] = false;
			}
		}

		// The energy level of each octopus increases by 1.
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, get(x, y) + 1);
			}
		}

		int prevSum = -1;
		while (true) {
			// Any octopus with an energy level greater than 9 flashes.
			for (int y = 0; y < getHeight(); y++) {
				for (int x = 0; x < getWidth(); x++) {
					if (get(x, y) > 9 && !flashedThisTurn[x][y]) {
						setFlashCount(getFlashCount() + 1);
						flashedThisTurn[x][y] = true;

						// This increases the energy level of all adjacent octopuses by 1.
						Pair<Integer> point = new Pair(x, y);
						List<Pair<Integer>> adjacent = point.getAdjacent(true);
						for (Pair<Integer> adj : adjacent) {
							if (adj.getX() >= 0 && adj.getX() < getWidth() && adj.getY() >= 0 && adj.getY() < getHeight()) {
								set(adj, get(adj) + 1);
							}
						}
					}
				}
			}

			// If no energy levels changed, time to end this turn.
			if (prevSum == getSum()) {
				break;
			}
			// Otherwise, check all octopuses again to see if the adjacent increases triggered a new flash.
			prevSum = getSum();
		}

		// When all flashing is done, set all flashed octopuses to 0.
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (flashedThisTurn[x][y]) {
					set(x, y, 0);
				}
			}
		}
	}

	/**
	 * Returns the sum of all energy levels in the grid.
	 */
	public int getSum() {
		int sum = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				sum += get(x, y);
			}
		}
		return (sum);
	}

	/**
	 * Accessor for the number of flashes that have occurred so far.
	 */
	public long getFlashCount() {
		return (_flashCount);
	}

	/**
	 * Accessor for the number of flashes that have occurred so far.
	 */
	public void setFlashCount(long flashCount) {
		_flashCount = flashCount;
	}
}