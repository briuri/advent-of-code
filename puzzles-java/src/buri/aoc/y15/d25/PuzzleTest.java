package buri.aoc.y15.d25;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.data.tuple.Pair;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(new Pair(2947, 3029));
		toConsole(result);
		assertEquals(19980801, result);
	}
}