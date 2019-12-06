package buri.aoc.y15.d15;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day15Test {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day15.getResult(Part.ONE, Day15.getInput(0));
		System.out.println("Day 15 Part 1\n\t" + result);
		assertEquals(18965440, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day15.getResult(Part.TWO, Day15.getInput(0));
		System.out.println("Day 15 Part 2\n\t" + result);
		assertEquals(15862900, result);
	}
}