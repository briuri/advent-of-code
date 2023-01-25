package buri.aoc.y17.d22;

import buri.aoc.common.data.Direction;
import buri.aoc.common.data.tuple.Pair;

/**
 * Data class for the virus carrier.
 *
 * @author Brian Uri!
 */
public class Virus {
	private Pair<Integer> _position;
	private Direction _direction;

	/**
	 * Constructor
	 */
	public Virus(Pair<Integer> position) {
		_position = position;
		setDirection(Direction.UP);
	}

	/**
	 * Part 1:
	 * If the current node is infected, it turns to its right. Otherwise, it turns to its left. (Turning is done
	 * in-place; the current node does not change.)
	 *
	 * Part 2:
	 * If it is clean, it turns left.
	 * If it is weakened, it does not turn, and will continue moving in the same direction.
	 * If it is infected, it turns right.
	 * If it is flagged, it reverses direction, and will go back the way it came.
	 */
	public void turn(long node) {
		if (node == Cluster.CLEAN) {
			setDirection(getDirection().turnLeft());
		}
		else if (node == Cluster.FLAGGED) {
			setDirection(getDirection().turnAround());
		}
		else if (node == Cluster.INFECTED) {
			setDirection(getDirection().turnRight());
		}
	}

	/**
	 * The virus carrier moves forward one node in the direction it is facing.
	 */
	public void move() {
		getPosition().move(getDirection());
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
}