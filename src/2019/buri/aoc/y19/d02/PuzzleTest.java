package buri.aoc.y19.d02;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(157, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3500L, Puzzle.getPart1Result(Puzzle.getInput(1), true));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getPart1Result(Puzzle.getInput(0), false);
		toConsole(result);
		assertEquals(4930687L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getPart2Result(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5335, result);
	}
}
