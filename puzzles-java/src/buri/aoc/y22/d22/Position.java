package buri.aoc.y22.d22;

import buri.aoc.common.data.Direction;
import buri.aoc.common.data.tuple.Pair;

import java.util.Objects;

/**
 * Data class for a point and a facing.
 *
 * @author Brian Uri!
 */
public class Position {

	private Pair<Integer> _point;
	private Direction _facing;

	/**
	 * Constructor
	 */
	public Position(int x, int y, Direction facing) {
		this(new Pair<>(x, y), facing);
	}

	/**
	 * Constructor
	 */
	public Position(Pair<Integer> point, Direction facing) {
		_point = point;
		_facing = facing;
	}

	/**
	 * Moves in the current direction.
	 */
	public void move() {
		getPoint().move(getFacing());
	}

	/**
	 * Creates a deep copy.
	 */
	public Position copy() {
		return new Position(getPoint().copy(), getFacing());
	}

	@Override
	public String toString() {
		return ("[" + getPoint() + " " + getFacing() + "]");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Position position = (Position) o;
		return Objects.equals(getPoint(), position.getPoint()) && getFacing() == position.getFacing();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPoint(), getFacing());
	}

	/**
	 * Accessor for the point
	 */
	public Pair<Integer> getPoint() {
		return _point;
	}

	/**
	 * Accessor for the direction facing
	 */
	public Direction getFacing() {
		return _facing;
	}

	/**
	 * Accessor for the direction facing
	 */
	public void setFacing(Direction facing) {
		_facing = facing;
	}
}