package buri.aoc.y15.d22;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day22Test extends BaseTest {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day22.getResult(Part.ONE);
		toConsole(result);
		assertEquals(953, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day22.getResult(Part.TWO);
		toConsole(result);
		assertEquals(1289, result);
	}
}
