package buri.aoc.y15.d10;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day10.getResult(Part.ONE, "1113222113");
		System.out.println("Day 10 Part 1\n\t" + result);
		assertEquals(252594, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day10.getResult(Part.TWO, "1113222113");
		System.out.println("Day 10 Part 2\n\t" + result);
		assertEquals(3579328, result);
	}
}
