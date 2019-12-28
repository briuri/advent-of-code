package buri.aoc.y16.d17;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.data.Direction;
import buri.aoc.data.MD5Hash;
import buri.aoc.data.tuple.Pair;

/**
 * Data class for a pair and associated set of doors around it.
 * 
 * @author Brian Uri!
 */
public class Position extends Pair {
	private String _passcodeSoFar;

	private static final MD5Hash HASHER = new MD5Hash();

	/**
	 * Constructor
	 */
	public Position(int x, int y, String passcodeSoFar) {
		super(x, y);
		_passcodeSoFar = passcodeSoFar;
	}

	/**
	 * Returns traversable cells adjacent to this position.
	 */
	public List<Position> getTraversableNeighbors() {
		List<Position> neighbors = new ArrayList<>();
		if (!(getX().intValue() == 3 && getY().intValue() == 3)) {
			if (canMove(Direction.UP)) {
				neighbors.add(new Position(getX().intValue(), getY().intValue() - 1, getPasscodeSoFar() + "U"));
			}
			if (canMove(Direction.LEFT)) {
				neighbors.add(new Position(getX().intValue() - 1, getY().intValue(), getPasscodeSoFar() + "L"));
			}
			if (canMove(Direction.RIGHT)) {
				neighbors.add(new Position(getX().intValue() + 1, getY().intValue(), getPasscodeSoFar() + "R"));
			}
			if (canMove(Direction.DOWN)) {
				neighbors.add(new Position(getX().intValue(), getY().intValue() + 1, getPasscodeSoFar() + "D"));
			}
		}
		return (neighbors);
	}

	/**
	 * Returns true if the direction is an unlocked door. Returns false for walls or locked doors.
	 */
	public boolean canMove(Direction direction) {
		boolean isWall = ((direction == Direction.LEFT && getX().intValue() == 0)
			|| (direction == Direction.UP && getY().intValue() == 0)
			|| (direction == Direction.RIGHT && getX().intValue() == 3)
			|| (direction == Direction.DOWN && getY().intValue() == 3));

		int index;
		switch (direction) {
			case UP:
				index = 0;
				break;
			case DOWN:
				index = 1;
				break;
			case LEFT:
				index = 2;
				break;
			default: // RIGHT
				index = 3;
				break;
		}
		return (!isWall && isDoorUnlocked(index));
	}

	/**
	 * Checks the hash character at the given index (UDLR) to see if a door is unlocked.
	 */
	private boolean isDoorUnlocked(int index) {
		char value = HASHER.getHash(getPasscodeSoFar()).charAt(index);
		return (value >= 'b' && value <= 'f');
	}

	@Override
	public boolean equals(Object obj) {
		Position p = (Position) obj;
		return (getX().equals(p.getX())
			&& getY().equals(p.getY()) && getPasscodeSoFar().equals(p.getPasscodeSoFar()));
	}

	@Override
	public int hashCode() {
		int result = getX().hashCode();
		result += getY().hashCode();
		result += getPasscodeSoFar().hashCode();
		return (result);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",").append(getY()).append(" ").append(getPasscodeSoFar());
		return (buffer.toString());
	}

	/**
	 * Accessor for the passcode plus all direction to reach here
	 */
	public String getPasscodeSoFar() {
		return _passcodeSoFar;
	}
}
