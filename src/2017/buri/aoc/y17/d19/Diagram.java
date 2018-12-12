package buri.aoc.y17.d19;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.data.AbstractGrid;
import buri.aoc.y17.d19.Position.Direction;

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
public class Diagram extends AbstractGrid {

	private int _steps = 0;
	private Position _current = null;
	private List<Character> _breadcrumbs = new ArrayList<>();

	private static final char BLANK = ' ';
	private static final char VERTICAL = '|';
	private static final char HORIZONTAL = '-';
	private static final char CORNER = '+';
	
	/**
	 * Constructor
	 */
	public Diagram(List<String> input) {
		super(input.get(0).length());
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				char value = line.charAt(x);
				if (value == VERTICAL && getCurrent() == null) {
					setCurrent(new Position(x, y));
				}
				set(new Position(x, y), (long) value);
			}
		}
	}
	
	/**
	 * Follows the path to the end and returns the string of letters.
	 */
	public String run() {
		while (isBounded(getCurrent()) && getCurrent().getDirection() != Direction.STOPPED) {
			setSteps(getSteps() + 1);
			getCurrent().move();
			char value = (char) get(getCurrent());
			if (value == HORIZONTAL || value == VERTICAL) {
				continue;
			}
			if (value == CORNER || value == BLANK) {
				getCurrent().setDirection(getNextDirection());
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
		Position up = new Position(getCurrent().getX(), getCurrent().getY() - 1);
		Position right = new Position(getCurrent().getX() + 1, getCurrent().getY());
		Position down = new Position(getCurrent().getX(), getCurrent().getY() + 1);
		Position left = new Position(getCurrent().getX() - 1, getCurrent().getY());
		Direction next = Direction.STOPPED;
		switch (getCurrent().getDirection()) {
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
			case STOPPED:
				// Fall through.
		}
		return (next);
	}
	
	/**
	 * Returns true if position is within the diagram.
	 */
	private boolean isBounded(Position position) {
		boolean xBounded = (position.getX() >= 0 && position.getX() < getSize());
		boolean yBounded = (position.getY() >= 0 && position.getY() < getSize());
		return (xBounded && yBounded);
	}
	
	@Override
	protected String toOutput(long value) {
		return (String.valueOf((char) value));
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
	 * Accessor for the current
	 */
	private Position getCurrent() {
		return _current;
	}

	/**
	 * Accessor for the current
	 */
	private void setCurrent(Position current) {
		_current = current;
	}

	/**
	 * Accessor for the breadcrumbs
	 */
	private List<Character> getBreadcrumbs() {
		return _breadcrumbs;
	}
}
