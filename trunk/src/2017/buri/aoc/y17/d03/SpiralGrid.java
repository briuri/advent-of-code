package buri.aoc.y17.d03;

import buri.aoc.data.AbstractGrid;

/**
 * Mutable data class for the spiral memory grid.
 * 
 * @author Brian Uri!
 */
public class SpiralGrid extends AbstractGrid {
	
	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public SpiralGrid(int size) {
		super(size);
	}
		
	/**
	 * Gets the sum of all adjacent and diagonal values from a position. (0,0) is the top left corner and (x, y) is the
	 * lower right corner.
	 */
	public int getSurroundingValues(Position position) {
		int sum = 0;
		if (position.getY() > 0) {
			// Upper Left
			if (position.getX() > 0) {
				sum += getGrid()[position.getX() - 1][position.getY() - 1];
			}
			// Left
			sum += getGrid()[position.getX()][position.getY() - 1];
			// Lower Left
			if (position.getX() < getGrid().length - 1) {
				sum += getGrid()[position.getX() + 1][position.getY() - 1];
			}
		}
		if (position.getX() > 0) {
			// Up
			sum += getGrid()[position.getX() - 1][position.getY()];
		}
		if (position.getX() < getGrid().length - 1) {
			// Down
			sum += getGrid()[position.getX() + 1][position.getY()];
		}
		if (position.getY() < getGrid().length - 1) {
			// Upper Right
			if (position.getX() > 0) {
				sum += getGrid()[position.getX() - 1][position.getY() + 1];
			}
			// Right
			sum += getGrid()[position.getX()][position.getY() + 1];
			// Lower Right
			if (position.getX() < getGrid().length - 1) {
				sum += getGrid()[position.getX() + 1][position.getY() + 1];
			}
		}
		return (sum);
	}
}
