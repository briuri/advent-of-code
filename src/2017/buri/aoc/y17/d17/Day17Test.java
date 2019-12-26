package buri.aoc.y17.d17;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day17Test extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(638, Day17.getResult(Part.ONE, 3));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day17.getResult(Part.ONE, 359);
		toConsole(result);
		assertEquals(1506, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day17.getResult(Part.TWO, 359);
		toConsole(result);
		assertEquals(39479736, result);
	}
}
