package buri.aoc.y17.d03;

import buri.aoc.data.AbstractPair;

/**
 * Mutable Integer-based ordered pair class, with commands to alter it by moving up, down, left, and right.
 * 
 * Moving is based on Java's array indexing:
 * 		(0,0) is the upper left corner of a grid.
 * 		(x,0) is lower left corner of a grid.
 * 		(0,y) is upper right corner of grid.
 * 		(x,y) is lower right corner of grid.
 * 
 * @author Brian Uri!
 */
public class Position extends AbstractPair {

	/**
	 * Constructor
	 */
	public Position(int x, int y) {
		super(x, y);
	}

	/**
	 * Moves one slot to the left.
	 */
	public void moveLeft() {
		setY(getY() - 1);
	}

	/**
	 * Moves one slot to the right.
	 */
	public void moveRight() {
		setY(getY() + 1);
	}

	/**
	 * Moves one slot down.
	 */
	public void moveDown() {
		setX(getX() + 1);
	}

	/**
	 * Moves one slot up.
	 */
	public void moveUp() {
		setX(getX() - 1);
	}
}
