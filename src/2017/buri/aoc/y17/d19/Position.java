package buri.aoc.y17.d19;

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

	private Direction _direction;
	
	public enum Direction {
		UP, RIGHT, DOWN, LEFT, STOPPED
	}
	
	/**
	 * Constructor
	 */
	public Position(int x, int y) {
		super(x, y);
		setDirection(Direction.DOWN);
	}

	/**
	 * Moves one slot in the current direction.
	 */
	public void move() {
		switch (getDirection()) {
			case UP:
				setY(getY() - 1);
				break;
			case RIGHT:
				setX(getX() + 1);
				break;
			case DOWN:
				setY(getY() + 1);
				break;
			case LEFT:
				setX(getX() - 1);
				break;
			case STOPPED:
				break;
		}
	}
	
	/**
	 * Accessor for the direction
	 */
	public Direction getDirection() {
		return _direction;
	}

	/**
	 * Accessor for the direction
	 */
	public void setDirection(Direction direction) {
		_direction = direction;
	}
}
