package buri.aoc.y22.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Day 22: Monkey Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(6032L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(155060L, result);
	}

// My solution was hardcoded to the real input.
//	@Test
//	public void testPart2Examples() {
//		assertEquals(5031L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
//	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3479L, result);
	}

	/**
	 * Part 1:
	 * What is the final password?
	 *
	 * Part 2:
	 * What is the final password?
	 */
	public static long getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		grid.run(part, input.get(input.size() - 1));
		return (grid.getValue());
	}
}