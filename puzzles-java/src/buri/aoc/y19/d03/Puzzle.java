package buri.aoc.y19.d03;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Direction;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 03: Crossed Wires
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(6L, 1, false);
		assertRun(731L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(30L, 1, false);
		assertRun(5672L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the Manhattan distance from the central port to the closest intersection?
	 *
	 * Part 2:
	 * What is the fewest combined steps the wires must take to reach an intersection?
	 */
	protected long runLong(Part part, List<String> input) {
		final int gridSize = input.get(0).length() < 20 ? 20 : 16000;
		CharGrid grid = new CharGrid(new Pair(gridSize, gridSize));
		final Pair<Integer> centralPort = grid.getCenterPosition();
		Set<Pair> intersections = new HashSet<>();
		char wireChar = 'A';
		for (String wirePath : input) {
			Pair<Integer> position = centralPort.copy();
			for (String token : wirePath.split(",")) {
				Direction direction = toDirection(token.charAt(0));
				int length = Integer.valueOf(token.substring(1));
				for (int i = 0; i < length; i++) {
					position.move(direction);
					char oldChar = grid.get(position);
					grid.set(position, wireChar);

					// Mark intersections while pathing out wire B.
					if (oldChar == (wireChar - 1)) {
						intersections.add(position.copy());
					}
				}
			}
			wireChar++;
		}
		if (part == Part.ONE) {
			long minDistance = Long.MAX_VALUE;
			for (Pair intersection : intersections) {
				minDistance = Math.min(minDistance, intersection.getManhattanDistance(centralPort));
			}
			return (minDistance);
		}

		// Part TWO
		long minSteps = Long.MAX_VALUE;
		for (Pair intersection : intersections) {
			int stepsA = countSteps(input.get(0), centralPort, intersection);
			int stepsB = countSteps(input.get(1), centralPort, intersection);
			int totalSteps = stepsA + stepsB;
			minSteps = Math.min(minSteps, totalSteps);
		}
		return (minSteps);
	}

	/**
	 * Converts a letter direction into an enum.
	 */
	private static Direction toDirection(char letter) {
		switch (letter) {
			case 'U':
				return (Direction.UP);
			case 'D':
				return (Direction.DOWN);
			case 'L':
				return (Direction.LEFT);
			case 'R':
				return (Direction.RIGHT);
		}
		throw new IllegalArgumentException("No direction for " + letter);
	}

	/**
	 * Retraces a wire until it reaches an intersection for the first time and returns the distance.
	 */
	protected static int countSteps(String wirePath, Pair centralPort, Pair intersection) {
		int steps = 0;
		Pair position = centralPort.copy();
		for (String token : wirePath.split(",")) {
			Direction direction = toDirection(token.charAt(0));
			int length = Integer.valueOf(token.substring(1));
			for (int i = 0; i < length; i++) {
				steps++;
				position.move(direction);
				if (position.equals(intersection)) {
					return (steps);
				}
			}
		}
		throw new RuntimeException("Never reached intersection.");
	}
}