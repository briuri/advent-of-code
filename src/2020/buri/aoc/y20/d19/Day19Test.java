package buri.aoc.y20.d19;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day19Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(600, Day19.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2L, Day19.getResult(Part.ONE, Day19.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day19.getResult(Part.ONE, Day19.getInput(0));
		toConsole(result);
		assertEquals(216L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3L, Day19.getResult(Part.ONE, Day19.getInput(2)));
		assertEquals(12L, Day19.getResult(Part.TWO, Day19.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day19.getResult(Part.TWO, Day19.getInput(0));
		toConsole(result);
		assertEquals(400L, result);
	}
}
