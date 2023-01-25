package buri.aoc.y22.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Day 12: Hill Climbing Algorithm
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(31L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(490L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(29L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(488L, result);
	}

	/**
	 * Part 1:
	 * What is the fewest steps required to move from your current position to the location that should get the best signal?
	 *
	 * Part 2:
	 * What is the fewest steps required to move starting from any square with elevation a to the location that should get the best signal?
	 */
	public static long getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		return (grid.getSteps(part));
	}
}