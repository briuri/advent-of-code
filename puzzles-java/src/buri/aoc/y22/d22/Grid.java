package buri.aoc.y22.d22;

import buri.aoc.common.Part;
import buri.aoc.common.data.Direction;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data model for the monkey map.
 *
 * @author Brian Uri!
 */
public class Grid extends CharGrid {

	private static final char OPEN = '.';
	private static final char BLANK = ' ';

	private Position _position;

	/**
	 * Hardcoded mapping of wrapping spots all around the cube. Designed specifically for my input:
	 * 			 000-049  050-099  100-149
	 *
	 * 000-049           1111111  2222222
	 * 050-099           3333333
	 * 100-149  4444444  5555555
	 * 150-199  6666666
	 *
	 * Example Wrapping (I worked this out but never coded it):
	 * 		1R - 6R (L) / 6R - 1R (L)
	 * 		4R - 6U (D) / 6U - 4R (L)
	 * 		2D - 5D (U) / 5D - 2D (U)
	 * 		3D - 5L (R) / 5L - 3D (U)
	 * 		6D - 2L (R) / 2L - 6D (U)
	 * 		1L - 3U (D) / 3U - 1L (R)
	 * 		1U - 2U (D) / 2U - 1U (D)
	 */
	private static final Map<Position, Position> WRAPS = new HashMap<>();
	static {
		for (int i = 0; i < 50; i++) {
			// 1U - 6L (R)
			WRAPS.put(new Position(50 + i, 0, Direction.UP), new Position(0, 150 + i, Direction.RIGHT));
			// 6L - 1U (D)
			WRAPS.put(new Position(0, 150 + i, Direction.LEFT), new Position(50 + i, 0, Direction.DOWN));

			// 2U - 6D (U)
			WRAPS.put(new Position(100 + i, 0, Direction.UP), new Position(i, 199, Direction.UP));
			// 6D - 2U (D)
			WRAPS.put(new Position(i, 199, Direction.DOWN), new Position(100 + i, 0, Direction.DOWN));

			// 4U - 3L (R)
			WRAPS.put(new Position(i, 100, Direction.UP), new Position(50, 50 + i, Direction.RIGHT));
			// 3L - 4U (D)
			WRAPS.put(new Position(50, 50 + i, Direction.LEFT), new Position(i, 100, Direction.DOWN));

			// 3R - 2D (U)
			WRAPS.put(new Position(99, 50 + i, Direction.RIGHT), new Position(100 + i, 49, Direction.UP));
			// 2D - 3R (L)
			WRAPS.put(new Position(100 + i, 49, Direction.DOWN), new Position(99, 50 + i, Direction.LEFT));

			// 6R - 5D (U)
			WRAPS.put(new Position(49, 150 + i, Direction.RIGHT), new Position(50 + i, 149, Direction.UP));
			// 5D - 6R (L)
			WRAPS.put(new Position(50 + i, 149, Direction.DOWN), new Position(49, 150 + i, Direction.LEFT));

			// 2R - 5R (L)
			WRAPS.put(new Position(149, i, Direction.RIGHT), new Position(99, 149 - i, Direction.LEFT));
			// 5R - 2R (L)
			WRAPS.put(new Position(99, 100 + i, Direction.RIGHT), new Position(149, 49 - i, Direction.LEFT));

			// 1L - 4L (R)
			WRAPS.put(new Position(50, i, Direction.LEFT), new Position(0, 149 - i, Direction.RIGHT));
			// 4L - 1L (R)
			WRAPS.put(new Position(0, 100 + i, Direction.LEFT), new Position(50, 49 - i, Direction.RIGHT));
		}
	}

	/**
	 * Constructor
	 */
	public Grid(List<String> input) {
		super(new Pair<>(input.get(0).length() + 5, input.size() - 2));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				char value = (x > input.get(y).length() - 1 ? BLANK : input.get(y).charAt(x));
				set(x, y, value);
			}
		}
		for (int x = 0; x < getWidth(); x++) {
			if (get(x, 0) != BLANK) {
				setPosition(new Position(x, 0, Direction.RIGHT));
				break;
			}
		}
	}

	/**
	 * Returns the password:
	 * 1000 times the row, 4 times the column, and the facing.
	 */
	public long getValue() {
		// RIGHT
		int facingScore = 0;
		if (getPosition().getFacing() == Direction.DOWN) {
			facingScore = 1;
		}
		if (getPosition().getFacing() == Direction.LEFT) {
			facingScore = 2;
		}
		if (getPosition().getFacing() == Direction.UP) {
			facingScore = 3;
		}
		return (1000 * (getPosition().getPoint().getY() + 1) + 4 * (getPosition().getPoint().getX() + 1) + facingScore);
	}

	/**
	 * Follows the directions through the map.
	 */
	public void run(Part part, String steps) {
		String noLetters = steps.replace('R', ' ').replace('L', ' ');
		String[] tokens = noLetters.split(" ");
		int index = 0;
		for (String token : tokens) {
			int distance = Integer.parseInt(token);
			index += token.length();
			char direction = (index <= steps.length() - 1) ? steps.charAt(index) : BLANK;
			index += 1;

			for (int i = 0; i < distance; i++) {
				Position next = getNextPosition(part);
				if (get(next.getPoint()) == OPEN) {
					setPosition(next.copy());
				}
				// Give up when we hit a wall.
				else {
					break;
				}
			}

			// Turn
			if (direction == 'L') {
				getPosition().setFacing(getPosition().getFacing().turnLeft());
			}
			else if (direction == 'R') {
				getPosition().setFacing(getPosition().getFacing().turnRight());
			}
		}
	}

	/**
	 * Finds the next spot to move to. If we need to wrap, follow part specific rules.
	 *
	 * In Part One, go to the other end of the row / col and search for the first non-blank spot.
	 *
	 * In Part Two, look up the wrapping destination in WRAPS based on a 3D cube.
	 */
	public Position getNextPosition(Part part) {
		Position position = getPosition();
		Position next = position.copy();
		next.move();

		if (!isInBounds(next.getPoint()) || get(next.getPoint()) == BLANK) {
			if (part == Part.TWO) {
				return (WRAPS.get(position));
			}

			// Part 1
			if (position.getFacing() == Direction.DOWN) {
				next.getPoint().setY(0);
			}
			if (position.getFacing() == Direction.UP) {
				next.getPoint().setY(getHeight() - 1);
			}
			if (position.getFacing() == Direction.RIGHT) {
				next.getPoint().setX(0);
			}
			if (position.getFacing() == Direction.LEFT) {
				next.getPoint().setX(getWidth() - 1);
			}
			while (get(next.getPoint()) == BLANK) {
				next.move();
			}
		}
		return next;
	}

	/**
	 * Accessor for the current position
	 */
	private Position getPosition() {
		return _position;
	}

	/**
	 * Accessor for the current position
	 */
	private void setPosition(Position position) {
		_position = position;
	}
}