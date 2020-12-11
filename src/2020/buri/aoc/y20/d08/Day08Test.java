package buri.aoc.y20.d08;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day08Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(654, Day08.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(5, Day08.getResult(Part.ONE, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day08.getResult(Part.ONE, Day08.getInput(0));
		toConsole(result);
		assertEquals(2025, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8, Day08.getResult(Part.TWO, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day08.getResult(Part.TWO, Day08.getInput(0));
		toConsole(result);
		assertEquals(2001, result);
	}
}
