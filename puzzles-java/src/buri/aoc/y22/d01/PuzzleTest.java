package buri.aoc.y22.d01;

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
		assertEquals(24000L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(66719L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(45000L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(198551L, result);
	}
}