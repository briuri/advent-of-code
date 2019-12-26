package buri.aoc.y17.d25;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class Day25Test extends BaseTest {

	@Test
	public void testGetInput() {
		Machine input = Day25.getInput(0);
		assertNotNull(input);
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day25.getResult(Day25.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day25.getResult(Day25.getInput(0));
		toConsole(result);
		assertEquals(3578, result);
	}
}
