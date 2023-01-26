package buri.aoc.y16.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 18: Like a Rogue
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1989L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(19999894L, 0, true);
	}

	/**
	 * Part 1:
	 * Starting with the map in your puzzle input, in a total of 40 rows (including the starting row), how many safe
	 * tiles are there?
	 *
	 * Part 2:
	 * How many safe tiles are there in a total of 400000 rows?
	 */
	protected long runLong(Part part, List<String> input) {
		int rows = (part == Part.ONE ? 40 : 400000);
		Grid grid = new Grid(input.get(0), rows);
		return (grid.getSafeCount(rows));
	}
}