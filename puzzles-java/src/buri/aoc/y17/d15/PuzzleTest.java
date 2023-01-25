package buri.aoc.y17.d15;

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
		assertEquals(588, Puzzle.getResult(Part.ONE, 65, 8921));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 634, 301);
		toConsole(result);
		assertEquals(573, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(309, Puzzle.getResult(Part.TWO, 65, 8921));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 634, 301);
		toConsole(result);
		assertEquals(294, result);
	}
}