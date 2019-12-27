package buri.aoc.y18.d09;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class Day09Test extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(32, Day09.getResult(9, 25));
		assertEquals(8317, Day09.getResult(10, 1618));
		assertEquals(146373, Day09.getResult(13, 7999));
		assertEquals(2764, Day09.getResult(17, 1104));
		assertEquals(54718, Day09.getResult(21, 6111));
		assertEquals(37305, Day09.getResult(30, 5807));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day09.getResult(411, 72059);
		toConsole(result);
		assertEquals(429943, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day09.getResult(411, 7205900);
		toConsole(result);
		assertEquals(3615691746L, result);
	}
}
