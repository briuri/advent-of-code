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

import static org.junit.Assert.assertEquals;

/**
 * Day 1: No Time for a Taxicab
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(5, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(2, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(12, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(307, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(165, result);
	}

	/**
	 * Part 1:
	 * How many blocks away is Easter Bunny HQ?
	 *
	 * Part 2:
	 * How many blocks away is the first location you visit twice?
	 */
	public static int getResult(Part part, List<String> input) {
		input = Arrays.asList(input.get(0).split(", "));
		Pair<Integer> position = followInstructions(part, input);
		return (Math.abs(position.getX()) + Math.abs(position.getY()));
	}

	/**
	 * Step through each instruction and return the desired position based on which part we're doing.
	 */
	private static Pair followInstructions(Part part, List<String> input) {
		// Start at the given coordinates and face North.
		Pair<Integer> position = new Pair(0, 0);
		Direction direction = Direction.UP;

		Set<Pair> visited = new HashSet<>();
		visited.add(position);
		for (String instruction : input) {
			direction = (instruction.charAt(0) == 'L' ? direction.turnLeft() : direction.turnRight());
			int distance = Integer.valueOf(instruction.substring(1));
			for (int i = 0; i < distance; i++) {
				switch (direction) {
					case UP:
						position = new Pair(position.getX(), position.getY() + 1);
						break;
					case RIGHT:
						position = new Pair(position.getX() + 1, position.getY());
						break;
					case DOWN:
						position = new Pair(position.getX(), position.getY() - 1);
						break;
					default: // LEFT
						position = new Pair(position.getX() - 1, position.getY());
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