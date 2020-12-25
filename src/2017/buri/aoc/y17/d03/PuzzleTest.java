package buri.aoc.y17.d03;

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
		assertEquals(0, Puzzle.getResult(Part.ONE, 1));
		assertEquals(3, Puzzle.getResult(Part.ONE, 12));
		assertEquals(2, Puzzle.getResult(Part.ONE, 23));
		assertEquals(31, Puzzle.getResult(Part.ONE, 1024));
	}

	/**
	 * Solves the Part 1 puzzle against the real input.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 312051);
		toConsole(result);
		assertEquals(430, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(747, Puzzle.getResult(Part.TWO, 700));
	}

	/**
	 * Solves the Part 2 puzzle against the real input.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 312051);
		toConsole(result);
		assertEquals(312453, result);
	}
}
