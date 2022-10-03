package buri.aoc.y19.d04;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertTrue(Puzzle.isValidPassword(Part.ONE, "111111"));
		assertFalse(Puzzle.isValidPassword(Part.ONE, "223450"));
		assertFalse(Puzzle.isValidPassword(Part.ONE, "123789"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1929, result);
	}

	@Test
	public void testPart2Examples() {
		assertTrue(Puzzle.isValidPassword(Part.TWO, "112233"));
		assertFalse(Puzzle.isValidPassword(Part.TWO, "123444"));
		assertTrue(Puzzle.isValidPassword(Part.TWO, "111122"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1306, result);
	}
}
