package buri.aoc.y19.d03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Direction;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Day 03: Crossed Wires
 * 
 * @author Brian Uri!
 */
public class Day03 extends BasePuzzle {

	/**
	 * Returns the input file unmodified, a wire path on each line.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * What is the Manhattan distance from the central port to the closest intersection?
	 * 
	 * Part 2:
	 * What is the fewest combined steps the wires must take to reach an intersection?
	 */
	public static int getResult(Part part, List<String> input, int gridSize) {
		CharGrid grid = new CharGrid(new Pair(gridSize, gridSize));
		final Pair centralPort = new Pair(grid.getWidth() / 2, grid.getHeight() / 2);
		Set<Pair> intersections = new HashSet<>();
		char wireChar = 'A';
		for (String wirePath : input) {
			Pair currentPos = centralPort.copy();
			for (String token : wirePath.split(",")) {
				Direction direction = toDirection(token.charAt(0));
				int length = Integer.valueOf(token.substring(1));
				for (int i = 0; i < length; i++) {
					currentPos.move(direction);
					char oldChar = grid.get(currentPos);
					grid.set(currentPos, wireChar);

					// Mark intersections while pathing out wire B.
					if (oldChar == (wireChar - 1)) {
						intersections.add(currentPos.copy());
					}
				}
			}
			wireChar++;
		}
		if (part == Part.ONE) {
			Map<Pair, Integer> mds = new HashMap<>();
			for (Pair intersection : intersections) {
				mds.put(intersection, getMDBetween(intersection, centralPort));
			}
			return (getMin(mds).getValue());
		}

		// Part TWO
		int minSteps = Integer.MAX_VALUE;
		for (Pair intersection : intersections) {
			int stepsA = calcSteps(input.get(0), centralPort, intersection);
			int stepsB = calcSteps(input.get(1), centralPort, intersection);
			int totalSteps = stepsA + stepsB;
			if (totalSteps < minSteps) {
				minSteps = totalSteps;
			}
		}
		return (minSteps);
	}

	/**
	 * Calculates the Manhattan distance between two positions.
	 */
	private static int getMDBetween(Pair p1, Pair p2) {
		return (Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()));
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
	protected static int calcSteps(String wirePath, Pair centralPort, Pair intersection) {
		int steps = 0;
		Pair currentPos = centralPort.copy();
		for (String token : wirePath.split(",")) {
			Direction direction = toDirection(token.charAt(0));
			int length = Integer.valueOf(token.substring(1));
			for (int i = 0; i < length; i++) {
				steps++;
				currentPos.move(direction);
				if (currentPos.equals(intersection)) {
					return (steps);
				}
			}
		}
		throw new RuntimeException("Never reached intersection.");
	}
}