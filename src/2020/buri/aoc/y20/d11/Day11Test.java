package buri.aoc.y20.d11;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day11Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(90, Day11.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(37, Day11.getResult(Part.ONE, Day11.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day11.getResult(Part.ONE, Day11.getInput(0));
		toConsole(result);
		assertEquals(2126, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(26, Day11.getResult(Part.TWO, Day11.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day11.getResult(Part.TWO, Day11.getInput(0));
		toConsole(result);
		assertEquals(1914, result);
	}
}
