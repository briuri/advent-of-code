package buri.aoc.y20.d25;

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
		assertEquals(1, Day25.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(0, Day25.getResult(Part.ONE, Day25.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day25.getResult(Part.ONE, Day25.getInput(0));
		toConsole(result);
		assertEquals(0, result);
	}
}
