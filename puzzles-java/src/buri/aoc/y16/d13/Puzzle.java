package buri.aoc.y16.d13;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 13: A Maze of Twisty Little Cubicles
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(90L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(135L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the fewest number of steps required for you to reach 31,39?
	 *
	 * Part 2:
	 * How many locations (distinct x,y coordinates, including your starting location) can you reach in at most 50
	 * steps?
	 */
	protected long runLong(Part part, List<String> input) {
		int magicNumber = Integer.parseInt(input.get(0));
		Pair<Integer> destination = new Pair<>(31, 39);
		Grid grid = new Grid(magicNumber);
		if (part == Part.ONE) {
			return (grid.getStepsTo(destination));
		}
		// Part TWO
		return (grid.getClosePositions(50));
	}
}