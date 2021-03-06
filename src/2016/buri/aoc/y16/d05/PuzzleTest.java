package buri.aoc.y16.d05;

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
		assertEquals("18f47a30", Puzzle.getResult(Part.ONE, "abc"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, "wtnhxymk");
		toConsole(result);
		assertEquals("2414bc77", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("05ace8e3", Puzzle.getResult(Part.TWO, "abc"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, "wtnhxymk");
		toConsole(result);
		assertEquals("437e60fc", result);
	}
}
