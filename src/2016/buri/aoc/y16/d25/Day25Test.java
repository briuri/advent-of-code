package buri.aoc.y16.d25;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day25Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(30, Day25.getInput(0).size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day25.getResult(Part.ONE, Day25.getInput(0));
		toConsole(result);
		assertEquals(175, result);
	}
}
