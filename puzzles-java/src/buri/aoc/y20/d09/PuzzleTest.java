package buri.aoc.y20.d09;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(127, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 5));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 25);
		toConsole(result);
		assertEquals(1930745883L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(62, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 5));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 25);
		toConsole(result);
		assertEquals(268878261L, result);
	}
}