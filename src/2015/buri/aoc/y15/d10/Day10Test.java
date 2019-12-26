package buri.aoc.y15.d10;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test extends BaseTest {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day10.getResult(Part.ONE, "1113222113");
		toConsole(result);
		assertEquals(252594, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day10.getResult(Part.TWO, "1113222113");
		toConsole(result);
		assertEquals(3579328, result);
	}
}
