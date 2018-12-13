package buri.aoc.y18.d13;

import buri.aoc.data.AbstractPair;

/**
 * Data model for the position of a cart.
 * 
 * @author Brian Uri!
 */
public class Position extends AbstractPair implements Comparable<Position> {

	/**
	 * Constructor
	 */
	public Position(int x, int y) {
		super(x, y);
	}

	/**
	 * Moves the position 1 step in a direction.
	 */
	public void move(Direction direction) {
		switch (direction) {
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
		}
	}
	
	@Override
	public String toString() {
		return (getX() + "," + getY());
	}

	@Override
	public boolean equals(Object obj) {
		Position position = (Position) obj;
		return (getX() == position.getX() && getY() == position.getY());
	}
	
	/**
	 * Carts are compared by their position (top-left first).
	 */
	@Override
	public int compareTo(Position o) {
		int compare = getY() - o.getY();
		if (compare == 0) {
			compare = getX() - o.getX();
		}
		return compare;
	}
}
