package buri.aoc.y18.d13;

import buri.aoc.common.data.Direction;
import buri.aoc.common.data.tuple.Pair;

/**
 * Data class for the cart.
 *
 * @author Brian Uri!
 */
public class Cart implements Comparable<Cart> {
	private final Pair<Integer> _position;
	private Direction _direction;
	private int _intersectionCount;

	/**
	 * Constructor
	 */
	public Cart(Pair<Integer> position, char icon) {
		_position = position;
		setDirection(Direction.getDirectionFor(icon));
		setIntersectionCount(0);
	}

	/**
	 * Moves the cart one slot in the direction it's facing.
	 */
	public void move() {
		getPosition().move(getDirection());
	}

	/**
	 * When a cart hits a corner, it turns the only way it can.
	 *
	 * When a cart arrives at an intersection, it turns left the first time, goes straight the second time, turns right
	 * the third time, and then repeats those directions. This process is independent of the particular intersection at
	 * which the cart has arrived - that is, the cart has no per-intersection memory.
	 */
	public void turn(char pathUnderCart) {
		switch (pathUnderCart) {
			case '-':
			case '|':
				break;
			case '/':
				if (getDirection() == Direction.UP || getDirection() == Direction.DOWN) {
					setDirection(getDirection().turnRight());
				}
				else if (getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT) {
					setDirection(getDirection().turnLeft());
				}
				break;
			case '\\':
				if (getDirection() == Direction.UP || getDirection() == Direction.DOWN) {
					setDirection(getDirection().turnLeft());
				}
				else if (getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT) {
					setDirection(getDirection().turnRight());
				}
				break;
			case '+':
				switch (getIntersectionCount()) {
					case (0):
						// Turn left
						setIntersectionCount(1);
						setDirection(getDirection().turnLeft());
						break;
					case (1):
						// Go straight
						setIntersectionCount(2);
						break;
					case (2):
						// Turn right
						setIntersectionCount(0);
						setDirection(getDirection().turnRight());
						break;
				}
				break;
		}
	}

	/**
	 * Carts are compared by their position (top-left first).
	 */
	@Override
	public int compareTo(Cart o) {
		return getPosition().compareTo(o.getPosition());
	}

	@Override
	public String toString() {
		return (getPosition() + " " + getDirection());
	}

	/**
	 * Accessor for the position
	 */
	public Pair<Integer> getPosition() {
		return _position;
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
	private void setDirection(Direction direction) {
		_direction = direction;
	}

	/**
	 * Accessor for the intersectionCount
	 */
	private int getIntersectionCount() {
		return _intersectionCount;
	}

	/**
	 * Accessor for the intersectionCount
	 */
	private void setIntersectionCount(int intersectionCount) {
		_intersectionCount = intersectionCount;
	}
}