package buri.aoc.y21.d03;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(198L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2972336L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(230L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3368358L, result);
	}
}