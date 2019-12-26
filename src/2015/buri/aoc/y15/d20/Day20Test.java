package buri.aoc.y15.d20;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day20Test extends BaseTest {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day20.getResult(Part.ONE, 33100000);
		toConsole(result);
		assertEquals(776160, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day20.getResult(Part.TWO, 33100000);
		toConsole(result);
		assertEquals(786240, result);
	}
}
