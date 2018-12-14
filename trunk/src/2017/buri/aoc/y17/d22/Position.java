package buri.aoc.y17.d22;

import buri.aoc.data.AbstractPair;

/**
 * Data model for the position of the virus carrier
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
}