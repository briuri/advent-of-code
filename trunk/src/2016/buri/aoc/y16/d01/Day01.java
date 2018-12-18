package buri.aoc.y16.d01;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.Pair;

/**
 * @author Brian Uri!
 */
public class Day01 extends Puzzle {
	
	private enum Direction {
		NORTH, EAST, SOUTH, WEST
	}
	
	/**
	 * Input: One line with all instructions
	 * Output: A list of instructions.
	 */
	public static List<String> getInput(int fileIndex) {
		return (Arrays.asList(readFile("2016/01", fileIndex).get(0).split(", ")));
	}
	
	/**
	 * Part 1:
	 * How many blocks away is Easter Bunny HQ?
	 * 
	 * Part 2:
	 * How many blocks away is the first location you visit twice?
	 */
	public static int getResult(Part part, List<String> input) {
		Pair position = followInstructions(part, input);
		return (Math.abs(position.getX()) + Math.abs(position.getY()));
	}
	
	/**
	 * Part 1:
	 * How many blocks away is Easter Bunny HQ?
	 * 
	 * Part 2:
	 * How many blocks away is the first location you visit twice?
	 */
	private static Pair followInstructions(Part part, List<String> input) {
		Pair position = new Pair(0, 0);
		Direction direction = Direction.NORTH;
		Set<Pair> visited = new HashSet<>();
		visited.add(position);
		for (String command : input) {
			direction = turn(direction, command.charAt(0));
			int distance = Integer.valueOf(command.substring(1));
			for (int i = 0; i < distance; i++) {
				switch (direction) {
					case NORTH:
						position = new Pair(position.getX(), position.getY() + 1);
						break;
					case EAST:
						position = new Pair(position.getX() + 1, position.getY());
						break;
					case SOUTH:
						position = new Pair(position.getX(), position.getY() - 1);
						break;
					case WEST:
						position = new Pair(position.getX() - 1, position.getY());
						break;
				}
				if (part == Part.TWO && visited.contains(position)) {
					return (position);
				}
				visited.add(position);
			}
		}
		return (position);
	}
	
	/**
	 * Rotates to a different direction.
	 */
	private static Direction turn(Direction direction, char turn) {
		switch (direction) {
			case NORTH:
				return (turn == 'L' ? Direction.WEST : Direction.EAST);
			case EAST:
				return (turn == 'L' ? Direction.NORTH : Direction.SOUTH);
			case SOUTH:
				return (turn == 'L' ? Direction.EAST : Direction.WEST);
			case WEST:
				return (turn == 'L' ? Direction.SOUTH : Direction.NORTH);
		}
		throw new IllegalArgumentException("Could not determine new direction.");
	}
}