package buri.aoc.y22.d14;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Day 14: Regolith Reservoir
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final Pair<Integer> START = new Pair<>(500, 0);

	private static final char OPEN = ' ';
	private static final char ROCK = '#';
	private static final char RESTING_SAND = 'o';

	/**
	 * Part 1:
	 * How many units of sand come to rest before sand starts flowing into the abyss below?
	 *
	 * Part 2:
	 * How many units of sand come to rest?
	 */
	public static long getResult(Part part, List<String> input) {
		// Eliminate duplicate lines in the walls.
		Set<String> uniqueInput = new HashSet<>(input);
		Map<Pair<Integer>, Character> grid = new HashMap<>();
		for (String line : uniqueInput) {
			String[] tokens = line.split(" -> ");
			for (int i = 0; i < tokens.length - 1; i++) {
				Pair<Integer> rockStart = new Pair<>(tokens[i], Integer.class);
				Pair<Integer> rockEnd = new Pair<>(tokens[i + 1], Integer.class);
				if (rockStart.getX().equals(rockEnd.getX())) {
					int minY = Math.min(rockStart.getY(), rockEnd.getY());
					int maxY = Math.max(rockStart.getY(), rockEnd.getY());
					for (int y = minY; y <= maxY; y++) {
						grid.put(new Pair<>(rockStart.getX(), y), ROCK);
					}
				}
				else if (rockStart.getY().equals(rockEnd.getY())) {
					int minX = Math.min(rockStart.getX(), rockEnd.getX());
					int maxX = Math.max(rockStart.getX(), rockEnd.getX());
					for (int x = minX; x <= maxX; x++) {
						grid.put(new Pair<>(x, rockStart.getY()), ROCK);
					}
				}
			}
		}

		int floorLevel = Integer.MIN_VALUE;
		for (Pair<Integer> position : grid.keySet()) {
			floorLevel = Math.max(floorLevel, position.getY());
		}

		// Add another floor in part 2. Stretch it enough to form a triangle with sand source.
		if (part == Part.TWO) {
			floorLevel = floorLevel + 2;
			for (int x = START.getX() - floorLevel; x <= START.getX() + floorLevel; x++) {
				grid.put(new Pair<>(x, floorLevel), ROCK);
			}
		}

		// Drop sand until an end condition is reached.
		Pair<Integer> start = START;
		while (start != null) {
			 start = dropSand(grid, start, floorLevel);
		}
		// Count the units of resting sand.
		int sum = 0;
		for (int value : grid.values()) {
			if (value == RESTING_SAND) {
				sum++;
			}
		}
		return sum;
	}

	/**
	 * Drops a unit of sand according to the rules:
	 * - Straight down if possible.
	 * - Down and left if possible.
	 * - Down and right if possible.
	 * - Otherwise, come to rest.
	 *
	 * Save a little time by starting the next sand drop from the previous location if known.
	 *
	 * Exit conditions:
	 * - Sand drops below the floorLevel.
	 * - Sand blocks the sand source tile.
	 */
	protected static Pair<Integer> dropSand(Map<Pair<Integer>, Character> grid, Pair<Integer> start, int floorLevel) {
		Pair<Integer> sand = start.copy();
		Pair<Integer> previous = sand;
		while (true) {
			// Don't drop more sand if we are falling into infinity.
			if (sand.getY() > floorLevel) {
				return null;
			}
			// Try each of the possible destinations in order.
			List<Pair<Integer>> options = new ArrayList<>();
			options.add(new Pair<>(sand.getX(), sand.getY() + 1));
			options.add(new Pair<>(sand.getX() - 1, sand.getY() + 1));
			options.add(new Pair<>(sand.getX() + 1, sand.getY() + 1));
			boolean moved = false;
			for (Pair<Integer> option : options) {
				if (grid.getOrDefault(option, OPEN) == OPEN) {
					previous = sand;
					sand = option;
					moved = true;
					break;
				}
			}
			// Come to rest.
			if (!moved) {
				grid.put(sand, RESTING_SAND);
				// Don't drop more sand if we have blocked the source.
				if (sand.equals(START)) {
					return null;
				}
				return (previous.equals(sand) ? START : previous);
			}
		}
	}
}