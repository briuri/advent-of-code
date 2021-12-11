package buri.aoc.y19.d01;

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
		assertEquals(2, Puzzle.getFuel(Part.ONE, 12));
		assertEquals(2, Puzzle.getFuel(Part.ONE, 14));
		assertEquals(654, Puzzle.getFuel(Part.ONE, 1969));
		assertEquals(33583, Puzzle.getFuel(Part.ONE, 100756));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3087896, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Puzzle.getFuel(Part.TWO, 14));
		assertEquals(966, Puzzle.getFuel(Part.TWO, 1969));
		assertEquals(50346, Puzzle.getFuel(Part.TWO, 100756));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4628989, result);
	}
}
