package buri.aoc.y16.d14;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day14Test extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(22728, Day14.getResult(Part.ONE, "abc"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day14.getResult(Part.ONE, "ihaygndm");
		toConsole(result);
		assertEquals(15035, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(22551, Day14.getResult(Part.TWO, "abc"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day14.getResult(Part.TWO, "ihaygndm");
		toConsole(result);
		assertEquals(19968, result);
	}
}
