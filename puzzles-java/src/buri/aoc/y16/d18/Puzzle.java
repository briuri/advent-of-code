package buri.aoc.y16.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 18: Like a Rogue
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(38, Puzzle.getResult(Part.ONE, Puzzle.getInput(1).get(0), 10));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0), 40);
		toConsole(result);
		assertEquals(1989, result);
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0), 400000);
		toConsole(result);
		assertEquals(19999894, result);
	}

	/**
	 * Part 1:
	 * Starting with the map in your puzzle input, in a total of 40 rows (including the starting row), how many safe
	 * tiles are there?
	 *
	 * Part 2:
	 * How many safe tiles are there in a total of 400000 rows?
	 */
	public static int getResult(Part part, String input, int rows) {
		Grid grid = new Grid(input, rows);
		return (grid.getSafeCount(rows));
	}
}