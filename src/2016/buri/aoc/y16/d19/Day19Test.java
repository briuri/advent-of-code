package buri.aoc.y16.d19;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day19Test {

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day19.getResult(Part.ONE, 5));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day19.getResult(Part.ONE, 3005290);
		System.out.println("Day 19 Part 1\n\t" + result);
		assertEquals(1816277, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day19.getResult(Part.TWO, 5));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day19.getResult(Part.TWO, 3005290);
		System.out.println("Day 19 Part 2\n\t" + result);
		assertEquals(1410967, result);
	}
}
