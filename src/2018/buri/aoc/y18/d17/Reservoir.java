package buri.aoc.y18.d17;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Model for the reservoir
 * 
 * @author Brian Uri!
 */
public class Reservoir extends CharGrid {
	private int _minBoundsY = Integer.MAX_VALUE;
	private int _maxBoundsY = Integer.MIN_VALUE;
	private int _reachableTiles = 0;
	private int _waterTiles = 0;

	private static final Pair SPRING_POS = new Pair(500, 0);
	private static final char SAND = '.';
	private static final char SPRING = '+';
	private static final char CLAY = '#';
	private static final char WATER = '~';
	private static final char REACHABLE = '|';

	/**
	 * Constructor
	 */
	public Reservoir(List<String> input) {
		super(new Pair(1860, 1860));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, SAND);
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
	 * Optimized by keeping track of spill points in a single iteration so we don't recursively reflow area that was
	 * already traversed. This cut running time down from 10 minutes to less than 1 second.
	 * 
	 * Part 1:
	 * Returns the total number of tiles that can be reached.
	 * 
	 * Part 2:
	 * Returns just the number of tiles that retain water.
	 */
	public int flow(Part part) {
		int reachableTiles = 0;
		Set<Pair> visitedSpillPoints = new HashSet<Pair>();
		while (true) {
			flowDown(SPRING_POS, visitedSpillPoints);
			int newReachableTiles = getReachableTiles() + getWaterTiles();
			if (reachableTiles == newReachableTiles) {
				break;
			}
			visitedSpillPoints.clear();
			reachableTiles = newReachableTiles;
		}
		if (part == Part.ONE) {
			return (reachableTiles);
		}
		return (getWaterTiles());
	}

	/**
	 * Marks all tiles below the top tile as REACHABLE until CLAY is hit. If the bottom position is contained by CLAY,
	 * it is filled with water. Otherwise, we mark tiles as REACHABLE to left and right until water can flow down again.
	 */
	private void flowDown(Pair top, Set<Pair> visitedSpillpoints) {
		// Short circuit if we already spilled from this position in this iteration.
		if (visitedSpillpoints.contains(top)) {
			return;
		}

		visitedSpillpoints.add(top);
		Pair bottom = getOpenTileBelow(top);
		for (int y = top.getY() + 1; y <= bottom.getY(); y++) {
			set(top.getX(), y, REACHABLE);
		}
		if (!isOffGrid(bottom)) {
			if (isContained(bottom)) {
				fillAcross(bottom);
			}
			else {
				Pair leftSpillPoint = flowAcross(bottom, true);
				if (leftSpillPoint != null) {
					flowDown(leftSpillPoint, visitedSpillpoints);
				}
				Pair rightSpillPoint = flowAcross(bottom, false);
				if (rightSpillPoint != null) {
					flowDown(rightSpillPoint, visitedSpillpoints);
				}
			}
		}
	}

	/**
	 * Flows water left until it hits CLAY or a hole in the CLAY to flow down from. Returns the spill point.
	 */
	private Pair flowAcross(Pair start, boolean goLeft) {
		if (goLeft) {
			for (int x = start.getX(); x >= 0; x--) {
				if (get(x, start.getY()) == CLAY) {
					break;
				}
				set(x, start.getY(), REACHABLE);
				char valueBelow = get(x, start.getY() + 1);
				if (valueBelow != CLAY && valueBelow != WATER) {
					return (new Pair(x, start.getY()));
				}
			}
		}
		else {
			for (int x = start.getX(); x < getWidth(); x++) {
				if (get(x, start.getY()) == CLAY) {
					break;
				}
				set(x, start.getY(), REACHABLE);
				char valueBelow = get(x, start.getY() + 1);
				if (valueBelow != CLAY && valueBelow != WATER) {
					return (new Pair(x, start.getY()));
				}
			}
		}
		return (null);
	}

	/**
	 * Fills a contained row with standing water, going left to CLAY, then right to CLAY from the start.
	 */
	private void fillAcross(Pair start) {
		for (int x = start.getX(); x >= 0; x--) {
			if (get(x, start.getY()) == CLAY) {
				break;
			}
			set(x, start.getY(), WATER);
		}
		for (int x = start.getX(); x < getWidth(); x++) {
			if (get(x, start.getY()) == CLAY) {
				break;
			}
			set(x, start.getY(), WATER);
		}
	}

	/**
	 * Finds the first position below the top one that is not WATER or CLAY.
	 */
	private Pair getOpenTileBelow(Pair top) {
		int y;
		for (y = top.getY(); y < getHeight(); y++) {
			char value = get(top.getX(), y);
			if (value == CLAY || value == WATER) {
				break;
			}
		}
		return (new Pair(top.getX(), y - 1));
	}

	/**
	 * Returns true if the position is at the bottom of the usable grid.
	 */
	private boolean isOffGrid(Pair position) {
		return (position.getY() + 1 == getHeight());
	}

	/**
	 * Returns true if the position has CLAY to the left and right, and a continous CLAY or WATER floor underneath it
	 * (between the left/right bounds).
	 */
	private boolean isContained(Pair position) {
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
		for (int x = position.getX(); x < getWidth(); x++) {
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
	 * Sets a value on the grid.
	 * 
	 * Also maintains a running count of REACHABLE or WATER tiles so we don't have to do a full array traversal every
	 * iteration.
	 */
	private void set(int x, int y, char value) {
		char oldValue = get(x, y);
		super.set(x, y, value);

		if (y >= getMinBoundsY() && y < getMaxBoundsY() + 1) {
			if (oldValue == SAND && value == REACHABLE) {
				setReachableTiles(getReachableTiles() + 1);
			}
			else if (oldValue == REACHABLE && value == WATER) {
				setReachableTiles(getReachableTiles() - 1);
				setWaterTiles(getWaterTiles() + 1);
			}
			else if (oldValue == SAND && value == WATER) {
				setWaterTiles(getWaterTiles() + 1);
			}
		}
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

	/**
	 * Accessor for the reachableTiles
	 */
	private int getReachableTiles() {
		return _reachableTiles;
	}

	/**
	 * Accessor for the reachableTiles
	 */
	private void setReachableTiles(int reachableTiles) {
		_reachableTiles = reachableTiles;
	}

	/**
	 * Accessor for the waterTiles
	 */
	private int getWaterTiles() {
		return _waterTiles;
	}

	/**
	 * Accessor for the waterTiles
	 */
	private void setWaterTiles(int waterTiles) {
		_waterTiles = waterTiles;
	}
}