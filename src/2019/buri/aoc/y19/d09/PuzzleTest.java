package buri.aoc.y19.d09;

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
		assertEquals(99, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(1219070632396864L, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(1125899906842624L, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2870072642L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(58534L, result);
	}
}
