package buri.aoc.y16.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Direction;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 1: No Time for a Taxicab
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(5L, 1, false);
		assertRun(2L, 2, false);
		assertRun(12L, 3, false);
		assertRun(307L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(4L, 4, false);
		assertRun(165L, 0, true);
	}

	/**
	 * Part 1:
	 * How many blocks away is Easter Bunny HQ?
	 *
	 * Part 2:
	 * How many blocks away is the first location you visit twice?
	 */
	protected long runLong(Part part, List<String> input) {
		input = Arrays.asList(input.get(0).split(", "));
		Pair<Integer> position = followInstructions(part, input);
		return (Math.abs(position.getX()) + Math.abs(position.getY()));
	}

	/**
	 * Step through each instruction and return the desired position based on which part we're doing.
	 */
	private static Pair<Integer> followInstructions(Part part, List<String> input) {
		// Start at the given coordinates and face North.
		Pair<Integer> position = new Pair<>(0, 0);
		Direction direction = Direction.UP;

		Set<Pair> visited = new HashSet<>();
		visited.add(position);
		for (String instruction : input) {
			direction = (instruction.charAt(0) == 'L' ? direction.turnLeft() : direction.turnRight());
			int distance = Integer.parseInt(instruction.substring(1));
			for (int i = 0; i < distance; i++) {
				switch (direction) {
					case UP:
						position = new Pair<>(position.getX(), position.getY() + 1);
						break;
					case RIGHT:
						position = new Pair<>(position.getX() + 1, position.getY());
						break;
					case DOWN:
						position = new Pair<>(position.getX(), position.getY() - 1);
						break;
					default: // LEFT
						position = new Pair<>(position.getX() - 1, position.getY());
				}
				if (part == Part.TWO && visited.contains(position)) {
					return (position);
				}
				visited.add(position);
			}
		}
		return (position);
	}
}