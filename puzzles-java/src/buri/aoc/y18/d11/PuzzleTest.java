package buri.aoc.y18.d11;

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
		assertEquals("33,45", Puzzle.getResult(Part.ONE, 18));
		assertEquals("21,61", Puzzle.getResult(Part.ONE, 42));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, 7139);
		toConsole(result);
		assertEquals("20,62", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("90,269,16", Puzzle.getResult(Part.TWO, 18));
		assertEquals("232,251,12", Puzzle.getResult(Part.TWO, 42));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, 7139);
		toConsole(result);
		assertEquals("229,61,16", result);
	}
}