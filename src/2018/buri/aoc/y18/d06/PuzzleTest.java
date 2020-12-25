package buri.aoc.y18.d06;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(50, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(17, Puzzle.getPart1Result(Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getPart1Result(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3251, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(16, Puzzle.getPart2Result(32, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getPart2Result(10000, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(47841, result);
	}
}
