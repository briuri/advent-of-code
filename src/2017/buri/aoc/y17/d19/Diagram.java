package buri.aoc.y17.d19;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.data.Direction;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for the routing diagram.
 * 
 * Its starting point is just off the top of the diagram. Lines (drawn with |, -, and +) show the path it needs to take,
 * starting by going down onto the only line connected to the top of the diagram. It needs to follow this path until it
 * reaches the end (located somewhere within the diagram) and stop there.
 * 
 * Sometimes, the lines cross over each other; in these cases, it needs to continue going the same direction, and only
 * turn left or right when there's no other option. In addition, someone has left letters on the line; these also don't
 * change its direction, but it can use them to keep track of where it's been.
 * 
 * @author Brian Uri!
 */
public class Diagram extends CharGrid {

	private int _steps = 0;
	private Pair<Integer> _currentPosition = null;
	private Direction _currentDirection = null;
	private List<Character> _breadcrumbs = new ArrayList<>();

	private static final char BLANK = ' ';
	private static final char VERTICAL = '|';
	private static final char HORIZONTAL = '-';
	private static final char CORNER = '+';

	/**
	 * Constructor
	 */
	public Diagram(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				char value = line.charAt(x);
				if (value == VERTICAL && getCurrentPosition() == null) {
					setCurrentPosition(new Pair(x, y));
					setCurrentDirection(Direction.DOWN);
				}
				set(x, y, value);
			}
		}
	}

	/**
	 * Follows the path to the end and returns the string of letters.
	 */
	public String run() {
		while (isBounded(getCurrentPosition()) && getCurrentDirection() != null) {
			setSteps(getSteps() + 1);
			getCurrentPosition().move(getCurrentDirection());
			char value = (char) get(getCurrentPosition());
			if (value == HORIZONTAL || value == VERTICAL) {
				continue;
			}
			if (value == CORNER || value == BLANK) {
				setCurrentDirection(getNextDirection());
			}
			else {
				getBreadcrumbs().add(value);
			}
		}
		StringBuffer buffer = new StringBuffer();
		for (Character crumb : getBreadcrumbs()) {
			buffer.append(crumb);
		}
		return (buffer.toString());
	}

	/**
	 * Determines the next direction at a corner. Assumes 1 true path and always 90 degrees.
	 */
	private Direction getNextDirection() {
		Pair<Integer> up = new Pair(getCurrentPosition().getX(), getCurrentPosition().getY() - 1);
		Pair<Integer> right = new Pair(getCurrentPosition().getX() + 1, getCurrentPosition().getY());
		Pair<Integer> down = new Pair(getCurrentPosition().getX(), getCurrentPosition().getY() + 1);
		Pair<Integer> left = new Pair(getCurrentPosition().getX() - 1, getCurrentPosition().getY());
		Direction next = null;
		if (getCurrentDirection() != null) {
			switch (getCurrentDirection()) {
				case UP:
				case DOWN:
					if (isBounded(left) && get(left) != BLANK) {
						next = Direction.LEFT;
					}
					else if (isBounded(right) && get(right) != BLANK) {
						next = Direction.RIGHT;
					}
					break;
				case RIGHT:
				case LEFT:
					if (isBounded(up) && get(up) != BLANK) {
						next = Direction.UP;
					}
					else if (isBounded(down) && get(down) != BLANK) {
						next = Direction.DOWN;
					}
					break;
			}
		}
		return (next);
	}

	/**
	 * Returns true if position is within the diagram.
	 */
	private boolean isBounded(Pair<Integer> position) {
		boolean xBounded = (position.getX() >= 0 && position.getX() < getWidth());
		boolean yBounded = (position.getY() >= 0 && position.getY() < getHeight());
		return (xBounded && yBounded);
	}

	/**
	 * Accessor for the steps
	 */
	public int getSteps() {
		return _steps;
	}

	/**
	 * Accessor for the steps
	 */
	public void setSteps(int steps) {
		_steps = steps;
	}

	/**
	 * Accessor for the current position
	 */
	private Pair<Integer> getCurrentPosition() {
		return _currentPosition;
	}

	/**
	 * Accessor for the current position
	 */
	private void setCurrentPosition(Pair currentPosition) {
		_currentPosition = currentPosition;
	}

	/**
	 * Accessor for the current direction
	 */
	private Direction getCurrentDirection() {
		return _currentDirection;
	}

	/**
	 * Accessor for the current direction
	 */
	private void setCurrentDirection(Direction currentDirection) {
		_currentDirection = currentDirection;
	}

	/**
	 * Accessor for the breadcrumbs
	 */
	private List<Character> getBreadcrumbs() {
		return _breadcrumbs;
	}
}
