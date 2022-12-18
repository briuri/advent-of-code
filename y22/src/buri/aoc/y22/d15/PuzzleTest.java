package buri.aoc.y22.d15;

import buri.aoc.BaseTest;
import buri.aoc.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(26L, Puzzle.getResult(Part.ONE, true, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, false, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5335787L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(56000011L, Puzzle.getResult(Part.TWO, true, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, false, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(13673971349056L, result);
	}
}