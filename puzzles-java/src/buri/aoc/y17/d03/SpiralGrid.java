package buri.aoc.y17.d03;

import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.tuple.Pair;

/**
 * Mutable data class for the spiral memory grid.
 *
 * @author Brian Uri!
 */
public class SpiralGrid extends IntGrid {

	/**
	 * Constructor
	 */
	public SpiralGrid(int size) {
		super(new Pair<>(size, size));
	}

	/**
	 * Gets the sum of all adjacent and diagonal values from a position. (0,0) is the top left corner and (x, y) is the
	 * lower right corner.
	 */
	public int getSurroundingValues(Pair<Integer> position) {
		int sum = 0;
		int x = position.getX();
		int y = position.getY();

		if (y > 0) {
			// Upper Left
			if (x > 0) {
				sum += get(x - 1, y - 1);
			}
			// Up
			sum += get(x, y - 1);
			// Upper Right
			if (x < getWidth() - 1) {
				sum += get(x + 1, y - 1);
			}
		}
		if (x > 0) {
			// Left
			sum += get(x - 1, y);
		}
		if (x < getWidth() - 1) {
			// Right
			sum += get(x + 1, y);
		}
		if (y < getHeight() - 1) {
			// Lower Left
			if (x > 0) {
				sum += get(x - 1, y + 1);
			}
			// Down
			sum += get(x, y + 1);
			// Lower Right
			if (x < getWidth() - 1) {
				sum += get(x + 1, y + 1);
			}
		}
		return (sum);
	}
}