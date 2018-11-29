package buri.aoc.y17.d03;

/**
 * Day03
 * 
 * Mutable Integer-based ordered pair class, with commands to alter it by moving up, down, left, and right.
 * 
 * Based on Java's array indexing:
 * 		(0,0) is the upper left corner of a grid.
 * 		(x,0) is lower left corner of a grid.
 * 		(0,y) is upper right corner of grid.
 * 		(x,y) is lower right corner of grid.
 * 
 * @author Brian Uri!
 */
public class GridPosition {
	private int _x;
	private int _y;

	/**
	 * Constructor
	 * 
	 * @param x the starting X value
	 * @param y the starting Y value
	 */
	public GridPosition(int x, int y) {
		_x = x;
		_y = y;
	}

	/**
	 * Moves one slot to the left.
	 */
	public void moveLeft() {
		_y = _y - 1;
	}

	/**
	 * Moves one slot to the right.
	 */
	public void moveRight() {
		_y = _y + 1;
	}

	/**
	 * Moves one slot down.
	 */
	public void moveDown() {
		_x = _x + 1;
	}

	/**
	 * Moves one slot up.
	 */
	public void moveUp() {
		_x = _x - 1;
	}

	@Override
	public String toString() {
		return ("(" + getX() + "," + getY() + ")");
	}

	/**
	 * Accessor for X
	 */
	public int getX() {
		return _x;
	}

	/**
	 * Accessor for Y
	 */
	public int getY() {
		return _y;
	}
}
