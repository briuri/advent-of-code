package buri.aoc.y19.d04;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day04Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(2, Day04.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertTrue(Day04.isValidPassword(Part.ONE, "111111"));
		assertFalse(Day04.isValidPassword(Part.ONE, "223450"));
		assertFalse(Day04.isValidPassword(Part.ONE, "123789"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, Day04.getInput(0));
		toConsole(result);
		assertEquals(1929, result);
	}

	@Test
	public void testPart2Examples() {
		assertTrue(Day04.isValidPassword(Part.TWO, "112233"));
		assertFalse(Day04.isValidPassword(Part.TWO, "123444"));
		assertTrue(Day04.isValidPassword(Part.TWO, "111122"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, Day04.getInput(0));
		toConsole(result);
		assertEquals(1306, result);
	}
}
