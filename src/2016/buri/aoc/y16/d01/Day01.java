package buri.aoc.y16.d01;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Direction;
import buri.aoc.data.tuple.Pair;

/**
 * Day 1: No Time for a Taxicab
 * 
 * @author Brian Uri!
 */
public class Day01 extends BasePuzzle {

	/**
	 * Returns input file as a list of individual instructions.
	 */
	public static List<String> getInput(int fileIndex) {
		return (Arrays.asList(readFile(fileIndex).get(0).split(", ")));
	}

	/**
	 * Part 1:
	 * How many blocks away is Easter Bunny HQ?
	 * 
	 * Part 2:
	 * How many blocks away is the first location you visit twice?
	 */
	public static int getResult(Part part, List<String> input) {
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