package buri.aoc.y16.d05;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day05Test {

	@Test
	public void testPart1Examples() {
		assertEquals("18f47a30", Day05.getResult(Part.ONE, "abc"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day05.getResult(Part.ONE, "wtnhxymk");
		System.out.println("Day 5 Part 1\n\t" + result);
		assertEquals("2414bc77", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("05ace8e3", Day05.getResult(Part.TWO, "abc"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day05.getResult(Part.TWO, "wtnhxymk");
		System.out.println("Day 5 Part 2\n\t" + result);
		assertEquals("437e60fc", result);
	}
}
