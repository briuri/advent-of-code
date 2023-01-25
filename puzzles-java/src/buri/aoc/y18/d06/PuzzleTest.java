package buri.aoc.y18.d06;

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
		assertEquals(17, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 0));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 0);
		toConsole(result);
		assertEquals(3251, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(16, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 32));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 10000);
		toConsole(result);
		assertEquals(47841, result);
	}
}