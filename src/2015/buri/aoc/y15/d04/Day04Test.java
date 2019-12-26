package buri.aoc.y15.d04;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day04Test extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(609043, Day04.getResult(Part.ONE, "abcdef"));
		assertEquals(1048970, Day04.getResult(Part.ONE, "pqrstuv"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, "iwrupvqb");
		toConsole(result);
		assertEquals(346386, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, "iwrupvqb");
		toConsole(result);
		assertEquals(9958218, result);
	}
}
