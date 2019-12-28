package buri.aoc.y15.d16;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(500, Day16.getInput(0).size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day16.getResult(Part.ONE, Day16.getInput(0));
		toConsole(result);
		assertEquals(40, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day16.getResult(Part.TWO, Day16.getInput(0));
		toConsole(result);
		assertEquals(241, result);
	}
}
