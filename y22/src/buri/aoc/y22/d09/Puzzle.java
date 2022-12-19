package buri.aoc.y22.d09;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Direction;
import buri.aoc.data.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 09: Rope Bridge
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many positions does the tail of the rope visit at least once?
	 *
	 * Part 2:
	 * How many positions does the tail of the rope visit at least once?
	 */
	public static long getResult(Part part, List<String> input) {
		// Build the rope.
		int numKnots = (part == Part.ONE ? 2 : 10);
		List<Pair<Integer>> knots = new ArrayList<>();
		for (int i = 0; i < numKnots; i++) {
			knots.add(new Pair<>(0, 0));
		}

		Set<Pair<Integer>> visited = new HashSet<>();
		for (String line : input) {
			String[] tokens = line.split(" ");
			Direction direction = getDirectionFor(tokens[0]);
			int distance = Integer.parseInt(tokens[1]);

			for (int i = 0; i < distance; i++) {
				knots.get(0).move(direction);
				for (int j = 1; j < knots.size(); j++) {
					pullRope(knots.get(j - 1), knots.get(j));
				}
				visited.add(knots.get(numKnots - 1).copy());
			}
		}
		return (visited.size());
	}

	/**
	 * Converts UDLR into a direction enum.
	 */
	protected static Direction getDirectionFor(String dir) {
		switch (dir) {
			case ("U"):
				return Direction.UP;
			case ("D"):
				return Direction.DOWN;
			case ("L"):
				return Direction.LEFT;
			default:
				return Direction.RIGHT;
		}
	}

	/**
	 * Returns true if the head and tail are adjacent or overlapping.
	 */
	protected static boolean isAdjacent(Pair<Integer> head, Pair<Integer> tail) {
		List<Pair<Integer>> neighbors = head.getAdjacent(true);
		return (neighbors.contains(tail));
	}

	/**
	 * Pulls the rope between two ordered knots.
	 */
	protected static void pullRope(Pair<Integer> head, Pair<Integer> tail) {
		if (!isAdjacent(head, tail)) {
			if (head.getY() < tail.getY()) {
				tail.move(Direction.UP);
				if (head.getX() < tail.getX()) {
					tail.move(Direction.LEFT);
				}
				else if (head.getX() > tail.getX()) {
					tail.move(Direction.RIGHT);
				}
			}
			else if (head.getY() > tail.getY()) {
				tail.move(Direction.DOWN);
				if (head.getX() < tail.getX()) {
					tail.move(Direction.LEFT);
				}
				else if (head.getX() > tail.getX()) {
					tail.move(Direction.RIGHT);
				}
			}
			else if (head.getX() < tail.getX()) {
				tail.move(Direction.LEFT);
				if (head.getY() < tail.getY()) {
					tail.move(Direction.UP);
				}
				else if (head.getY() > tail.getY()) {
					tail.move(Direction.DOWN);
				}
			}
			else if (head.getX() > tail.getX()) {
				tail.move(Direction.RIGHT);
				if (head.getY() < tail.getY()) {
					tail.move(Direction.UP);
				}
				else if (head.getY() > tail.getY()) {
					tail.move(Direction.DOWN);
				}
			}
		}
	}

}