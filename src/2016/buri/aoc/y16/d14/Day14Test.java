package buri.aoc.y16.d14;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day14Test {

	@Test
	public void testPart1Examples() {
		assertEquals(22728, Day14.getResult(Part.ONE, "abc"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day14.getResult(Part.ONE, "ihaygndm");
		System.out.println("Day 14 Part 1\n\t" + result);
		assertEquals(15035, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(22551, Day14.getResult(Part.TWO, "abc"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day14.getResult(Part.TWO, "ihaygndm");
		System.out.println("Day 14 Part 2\n\t" + result);
		assertEquals(19968, result);
	}
}
