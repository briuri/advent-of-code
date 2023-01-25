package buri.aoc.y22.d18;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(10L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(64L, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4504L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(10L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(58L, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2556L, result);
	}
}