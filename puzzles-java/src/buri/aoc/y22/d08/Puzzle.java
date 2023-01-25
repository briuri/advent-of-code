package buri.aoc.y22.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Day 08: Treetop Tree House
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(21L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		// 1339
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1543L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(595080L, result);
	}

	/**
	 * Part 1:
	 * How many trees are visible from outside the grid?
	 *
	 * Part 2:
	 * What is the highest scenic score possible for any tree?
	 */
	public static long getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		return (grid.getAnswer(part));
	}
}