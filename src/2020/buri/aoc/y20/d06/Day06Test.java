package buri.aoc.y20.d06;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day06Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(454, Day06.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(11, Day06.getResult(Part.ONE, Day06.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day06.getResult(Part.ONE, Day06.getInput(0));
		toConsole(result);
		assertEquals(6335, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(6, Day06.getResult(Part.TWO, Day06.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day06.getResult(Part.TWO, Day06.getInput(0));
		toConsole(result);
		assertEquals(3392, result);
	}
}
