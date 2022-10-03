package buri.aoc.y20.d11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Direction;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Day 11: Seating System
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final char FLOOR = '.';
	private static final char EMPTY = 'L';
	private static final char OCCUPIED = '#';

	/**
	 * Part 1:
	 * How many seats end up occupied?
	 *
	 * Part 2:
	 * Given the new visibility method and the rule change for occupied seats becoming empty, once equilibrium is
	 * reached, how many seats end up occupied?
	 */
	public static int getResult(Part part, List<String> input) {
		CharGrid grid = new CharGrid(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				grid.set(x, y, input.get(y).charAt(x));
			}
		}

		Set<String> states = new HashSet<>();
		final int occupiedThreshold = (part == Part.ONE ? 4 : 5);
		while (true) {
			CharGrid next = new CharGrid(new Pair(grid.getWidth(), grid.getHeight()));
			for (int y = 0; y < grid.getHeight(); y++) {
				for (int x = 0; x < grid.getWidth(); x++) {
					Pair<Integer> center = new Pair(x, y);
					int adjacentFullSeats = countAdjacentOccupiedSeats(part, grid, center);
					char value = grid.get(center);

					// If empty seat has no occupied adjacent seats, it becomes occupied.
					if (value == EMPTY) {
						next.set(center, adjacentFullSeats == 0 ? OCCUPIED : EMPTY);
					}
					// If occupied seat has at least [occupiedThreshold] occupied adjacent seats, it becomes empty.
					else if (value == OCCUPIED) {
						next.set(center, adjacentFullSeats < occupiedThreshold ? OCCUPIED : EMPTY);
					}
					else {
						next.set(center, FLOOR);
					}
				}
			}
			// If we have reached a stable state, count the number of occupied seats.
			if (states.contains(next.toString())) {
				return (next.count(OCCUPIED));
			}
			// Otherwise keep applying the rules.
			states.add(next.toString());
			grid = next;
		}
	}

	/**
	 * Counts the seats adjacent to some center point. The definition of "adjacent" in Part One is 8 directly adjacent
	 * squares. The definition in Part Two is the first non-floor square visible in each of the 8 directions.
	 */
	protected static int countAdjacentOccupiedSeats(Part part, CharGrid grid, Pair<Integer> center) {
		List<Pair<Integer>> adjacents = new ArrayList<>();
		if (part == Part.ONE) {
			// Four cardinal directions.
			adjacents.addAll(center.getAdjacent());
			// Add diagonals.
			adjacents.add(new Pair(center.getX() - 1, center.getY() - 1));
			adjacents.add(new Pair(center.getX() + 1, center.getY() - 1));
			adjacents.add(new Pair(center.getX() + 1, center.getY() + 1));
			adjacents.add(new Pair(center.getX() - 1, center.getY() + 1));
		}
		else {
			// Four cardinal directions.
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.UP }));
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.RIGHT }));
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.DOWN }));
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.LEFT }));
			// Add diagonals.
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.UP, Direction.RIGHT }));
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.DOWN, Direction.RIGHT }));
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.DOWN, Direction.LEFT }));
			adjacents.add(findVisibleSeat(grid, center, new Direction[] { Direction.UP, Direction.LEFT }));
		}

		// Now count the occupied seats at all adjacent points.
		int count = 0;
		for (Pair<Integer> adjacent : adjacents) {
			if (adjacent.getX() >= 0 && adjacent.getX() < grid.getWidth()
				&& adjacent.getY() >= 0 && adjacent.getY() < grid.getHeight()
				&& grid.get(adjacent) == OCCUPIED) {
				count++;
			}
		}
		return (count);
	}

	/**
	 * Keeps pushing a point in some direction until it's off the grid or finds non-floor tiles.
	 */
	protected static Pair<Integer> findVisibleSeat(CharGrid grid, Pair center, Direction[] directions) {
		Pair<Integer> adjacent = center.copy();
		for (int i = 0; i < directions.length; i++) {
			adjacent.move(directions[i]);
		}

		while (adjacent.getX() >= 0 && adjacent.getX() < grid.getWidth()
			&& adjacent.getY() >= 0	&& adjacent.getY() < grid.getHeight()
			&& grid.get(adjacent) == FLOOR) {
			for (int i = 0; i < directions.length; i++) {
				adjacent.move(directions[i]);
			}
		}
		return (adjacent);
	}
}