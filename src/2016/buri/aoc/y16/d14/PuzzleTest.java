package buri.aoc.y16.d14;

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
		assertEquals(22728, Puzzle.getResult(Part.ONE, "abc"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, "ihaygndm");
		toConsole(result);
		assertEquals(15035, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(22551, Puzzle.getResult(Part.TWO, "abc"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, "ihaygndm");
		toConsole(result);
		assertEquals(19968, result);
	}
}
