package buri.aoc.y20.d09;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day09Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1000, Day09.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(127, Day09.getResult(Part.ONE, Day09.getInput(1), 5));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day09.getResult(Part.ONE, Day09.getInput(0), 25);
		toConsole(result);
		assertEquals(1930745883L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(62, Day09.getResult(Part.TWO, Day09.getInput(1), 5));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day09.getResult(Part.TWO, Day09.getInput(0), 25);
		toConsole(result);
		assertEquals(268878261L, result);
	}
}
