package buri.aoc.y16.d13;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(11, Puzzle.getResult(Part.ONE, 10, new Pair(7, 4)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 1352, new Pair(31, 39));
		toConsole(result);
		assertEquals(90, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 1352, new Pair(31, 39));
		toConsole(result);
		assertEquals(135, result);
	}
}