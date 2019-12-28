package buri.aoc.y15.d05;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day05Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1000, Day05.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day05.getResult(Part.ONE, Day05.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day05.getResult(Part.ONE, Day05.getInput(0));
		toConsole(result);
		assertEquals(238, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day05.getResult(Part.TWO, Day05.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day05.getResult(Part.TWO, Day05.getInput(0));
		toConsole(result);
		assertEquals(69, result);
	}
}
