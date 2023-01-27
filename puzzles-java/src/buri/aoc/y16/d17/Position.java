package buri.aoc.y16.d17;

import buri.aoc.common.data.Direction;
import buri.aoc.common.data.MD5Hash;
import buri.aoc.common.data.path.StepStrategy;
import buri.aoc.common.data.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class for a pair and associated set of doors around it.
 *
 * @author Brian Uri!
 */
public class Position extends Pair<Integer> {
	private final String _passcodeSoFar;

	private static final MD5Hash HASHER = new MD5Hash();

	protected static final StepStrategy<Position> STEP_STRATEGY = new StepStrategy<Position>() {
		@Override
		public List<Position> getNextSteps(Position current) {
			List<Position> nextSteps = new ArrayList<>();
			if (!(current.getX() == 3 && current.getY() == 3)) {
				if (canMove(current, Direction.UP)) {
					nextSteps.add(new Position(current.getX(), current.getY() - 1, current.getPasscodeSoFar() + "U"));
				}
				if (canMove(current, Direction.LEFT)) {
					nextSteps.add(new Position(current.getX() - 1, current.getY(), current.getPasscodeSoFar() + "L"));
				}
				if (canMove(current, Direction.RIGHT)) {
					nextSteps.add(new Position(current.getX() + 1, current.getY(), current.getPasscodeSoFar() + "R"));
				}
				if (canMove(current, Direction.DOWN)) {
					nextSteps.add(new Position(current.getX(), current.getY() + 1, current.getPasscodeSoFar() + "D"));
				}
			}
			return (nextSteps);
		}

		/**
		 * Returns true if the direction is an unlocked door. Returns false for walls or locked doors.
		 */
		private boolean canMove(Position current, Direction direction) {
			boolean isWall = ((direction == Direction.LEFT && current.getX() == 0)
				|| (direction == Direction.UP && current.getY() == 0)
				|| (direction == Direction.RIGHT && current.getX() == 3)
				|| (direction == Direction.DOWN && current.getY() == 3));

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
			return (!isWall && isDoorUnlocked(current, index));
		}

		/**
		 * Checks the hash character at the given index (UDLR) to see if a door is unlocked.
		 */
		private boolean isDoorUnlocked(Position current, int index) {
			char value = HASHER.getHash(current.getPasscodeSoFar()).charAt(index);
			return (value >= 'b' && value <= 'f');
		}
	};

	/**
	 * Constructor
	 */
	public Position(int x, int y, String passcodeSoFar) {
		super(x, y);
		_passcodeSoFar = passcodeSoFar;
	}

	@Override
	public boolean equals(Object obj) {
		Position p = (Position) obj;
		return (getX().equals(p.getX()) && getY().equals(p.getY()) && getPasscodeSoFar().equals(p.getPasscodeSoFar()));
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
		return (getX() + "," + getY() + " " + getPasscodeSoFar());
	}

	/**
	 * Accessor for the passcode plus all direction to reach here
	 */
	public String getPasscodeSoFar() {
		return _passcodeSoFar;
	}
}