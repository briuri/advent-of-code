package buri.aoc.y17.d03;

import buri.aoc.data.Pair;
import buri.aoc.data.grid.IntGrid;

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
		super(new Pair(size, size));
	}

	/**
	 * Gets the sum of all adjacent and diagonal values from a position. (0,0) is the top left corner and (x, y) is the
	 * lower right corner.
	 */
	public int getSurroundingValues(Pair position) {
		int sum = 0;
		if (position.getY() > 0) {
			// Upper Left
			if (position.getX() > 0) {
				sum += get(position.getX() - 1, position.getY() - 1);
			}
			// Up
			sum += get(position.getX(), position.getY() - 1);
			// Upper Right
			if (position.getX() < getWidth() - 1) {
				sum += get(position.getX() + 1, position.getY() - 1);
			}
		}
		if (position.getX() > 0) {
			// Left
			sum += get(position.getX() - 1, position.getY());
		}
		if (position.getX() < getWidth() - 1) {
			// Right
			sum += get(position.getX() + 1, position.getY());
		}
		if (position.getY() < getHeight() - 1) {
			// Left Left
			if (position.getX() > 0) {
				sum += get(position.getX() - 1, position.getY() + 1);
			}
			// Down
			sum += get(position.getX(), position.getY() + 1);
			// Lower Right
			if (position.getX() < getWidth() - 1) {
				sum += get(position.getX() + 1, position.getY() + 1);
			}
		}
		return (sum);
	}
}
