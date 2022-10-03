package buri.aoc.y21.d23;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(12521L, Puzzle.getResult(Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(18195L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Puzzle.getInput(1));
		toConsole(result);
		assertEquals(50265L, result);
	}
}
