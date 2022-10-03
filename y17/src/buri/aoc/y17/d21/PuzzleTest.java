package buri.aoc.y17.d21;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(12, Puzzle.getResult(Puzzle.getInput(1), 2));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Puzzle.getInput(0), 5);
		toConsole(result);
		assertEquals(139, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Puzzle.getInput(0), 18);
		toConsole(result);
		assertEquals(1857134, result);
	}
}
