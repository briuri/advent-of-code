package buri.aoc.y18.d17;

import java.util.List;

import buri.aoc.Part;

/**
 * Model for the reservoir
 * 
 * @author Brian Uri!
 */
public class Reservoir {
	private char[][] _grid;
	private int _minBoundsY = Integer.MAX_VALUE;
	private int _maxBoundsY = Integer.MIN_VALUE;

	private static final Position SPRING_POS = new Position(500, 0);
	private static final char OPEN = '.';
	private static final char SPRING = '+';
	private static final char CLAY = '#';
	private static final char WATER = '~';
	private static final char REACHABLE = '|';

	/**
	 * Constructor
	 */
	public Reservoir(List<String> input) {
		_grid = new char[1860][1860];
		for (int y = 0; y < getGrid().length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				set(x, y, OPEN);
			}
		}
		set(SPRING_POS.getX(), SPRING_POS.getY(), SPRING);

		for (String line : input) {
			String[] tokens = line.split(", ");
			int minX = 0;
			int maxX = 0;
			int minY = 0;
			int maxY = 0;
			for (String token : tokens) {
				String[] data = token.substring(token.indexOf("=") + 1, token.length()).split("\\.\\.");
				if (token.startsWith("x=")) {
					minX = Integer.valueOf(data[0]);
					maxX = (data.length == 2 ? Integer.valueOf(data[1]) : minX);
				}
				else {
					minY = Integer.valueOf(data[0]);
					maxY = (data.length == 2 ? Integer.valueOf(data[1]) : minY);
					_minBoundsY = Math.min(minY, _minBoundsY);
					_maxBoundsY = Math.max(maxY, _maxBoundsY);
				}
			}
			for (int y = minY; y < maxY + 1; y++) {
				for (int x = minX; x < maxX + 1; x++) {
					set(x, y, CLAY);
				}
			}
		}
	}

	/**
	 * Simulates water flowing from the spring until no additional tiles are reached.
	 * 
	 * Part 1:
	 * Returns the total number of tiles that can be reached.
	 * 
	 * Part 2:
	 * Returns just the number of tiles that retain water.
	 */
	public int flow(Part part) {
		int reachableTiles = 0;
		while (true) {
			flowDown(SPRING_POS);
			int newReachableTiles = countReachableTiles(true);
			if (reachableTiles == newReachableTiles) {
				break;
			}
			reachableTiles = newReachableTiles;
		}
		if (part == Part.ONE) {
			return (reachableTiles);
		}
		return (countReachableTiles(false));
	}

	/**
	 * Marks all tiles below the top tile as REACHABLE until CLAY is hit. If the bottom position is contained by CLAY,
	 * it is filled with water. Otherwise, we mark tiles as REACHABLE to left and right until water can flow down again.
	 */
	private void flowDown(Position top) {
		Position bottom = getOpenTileBelow(top);
		for (int y = top.getY() + 1; y <= bottom.getY(); y++) {
			set(top.getX(), y, REACHABLE);
		}
		if (!isOffGrid(bottom)) {
			if (isContained(bottom)) {
				fillAcross(bottom);
			}
			else {
				Position leftSpillPoint = flowAcross(bottom, true);
				if (leftSpillPoint != null) {
					flowDown(leftSpillPoint);
				}
				Position rightSpillPoint = flowAcross(bottom, false);
				if (rightSpillPoint != null) {
					flowDown(rightSpillPoint);
				}
			}
		}
	}

	/**
	 * Flows water left until it hits CLAY or a hole in the CLAY to flow down from. Returns the spill point.
	 */
	private Position flowAcross(Position start, boolean goLeft) {
		if (goLeft) {
			for (int x = start.getX(); x >= 0; x--) {
				if (get(x, start.getY()) == CLAY) {
					break;
				}
				set(x, start.getY(), REACHABLE);
				char valueBelow = get(x, start.getY() + 1);
				if (valueBelow != CLAY && valueBelow != WATER) {
					return (new Position(x, start.getY()));
				}
			}
		}
		else {
			for (int x = start.getX(); x < getGrid().length; x++) {
				if (get(x, start.getY()) == CLAY) {
					break;
				}
				set(x, start.getY(), REACHABLE);
				char valueBelow = get(x, start.getY() + 1);
				if (valueBelow != CLAY && valueBelow != WATER) {
					return (new Position(x, start.getY()));
				}
			}
		}
		return (null);
	}

	/**
	 * Fills a contained row with standing water, going left to CLAY, then right to CLAY from the start.
	 */
	private void fillAcross(Position start) {
		for (int x = start.getX(); x >= 0; x--) {
			if (get(x, start.getY()) == CLAY) {
				break;
			}
			set(x, start.getY(), WATER);
		}
		for (int x = start.getX(); x < getGrid().length; x++) {
			if (get(x, start.getY()) == CLAY) {
				break;
			}
			set(x, start.getY(), WATER);
		}
	}

	/**
	 * Finds the first position below the top one that is not WATER or CLAY.
	 */
	private Position getOpenTileBelow(Position top) {
		int y;
		for (y = top.getY(); y < getGrid().length; y++) {
			char value = get(top.getX(), y);
			if (value == CLAY || value == WATER) {
				break;
			}
		}
		return (new Position(top.getX(), y - 1));
	}

	/**
	 * Returns true if the position is at the bottom of the usable grid.
	 */
	private boolean isOffGrid(Position position) {
		return (position.getY() + 1 == getGrid().length);
	}

	/**
	 * Returns true if the position has CLAY to the left and right, and a continous CLAY or WATER floor underneath it
	 * (between the left/right bounds).
	 */
	private boolean isContained(Position position) {
		int minX = -1;
		int maxX = -1;

		// Check for left wall.
		for (int x = position.getX(); x >= 0; x--) {
			if (get(x, position.getY()) == CLAY) {
				minX = x;
				break;
			}
		}

		// Check for right wall.
		for (int x = position.getX(); x < getGrid().length; x++) {
			if (get(x, position.getY()) == CLAY) {
				maxX = x;
				break;
			}
		}

		// Short circuit if both walls don't exist.
		if (minX == -1 || maxX == -1) {
			return (false);
		}

		// Check for a floor.
		int floorY = position.getY() + 1;
		boolean hasFloor = true;
		for (int x = minX + 1; x < maxX; x++) {
			char value = get(x, floorY);
			hasFloor = hasFloor && (value == CLAY || value == WATER);
		}
		return (hasFloor);
	}

	/**
	 * Counts all the tiles that are in the path of the water flow. For Part 1, we want all REACHABLE and WATER tiles.
	 * For Part 2, we just want WATER tiles.
	 */
	private int countReachableTiles(boolean includeReachable) {
		int sum = 0;
		for (int y = getMinBoundsY(); y < getMaxBoundsY() + 1; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				char value = get(x, y);
				if (value == WATER || (includeReachable && value == REACHABLE)) {
					sum++;
				}
			}
		}
		return (sum);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getGrid().length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				buffer.append(get(x, y));
			}
			buffer.append("\n");
		}
		buffer.append("\n");
		return (buffer.toString());
	}

	/**
	 * Gets a value on the grid.
	 */
	private char get(int x, int y) {
		return (getGrid()[x][y]);
	}

	/**
	 * Sets a value on the grid.
	 */
	private void set(int x, int y, char value) {
		getGrid()[x][y] = value;
	}

	/**
	 * Accessor for the raw grid.
	 */
	private char[][] getGrid() {
		return (_grid);
	}

	/**
	 * Accessor for the minBoundsY
	 */
	private int getMinBoundsY() {
		return _minBoundsY;
	}

	/**
	 * Accessor for the maxBoundsY
	 */
	private int getMaxBoundsY() {
		return _maxBoundsY;
	}
}