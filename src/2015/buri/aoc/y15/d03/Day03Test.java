package buri.aoc.y15.d03;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day03Test {

	@Test
	public void testGetInput() {
		String input = Day03.getInput(0);
		assertEquals(8192, input.length());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day03.getResult(Part.ONE, Day03.getInput(0));
		System.out.println("Day 3 Part 1\n\t" + result);
		assertEquals(2565, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day03.getResult(Part.TWO, Day03.getInput(0));
		System.out.println("Day 3 Part 2\n\t" + result);
		assertEquals(2639, result);
	}
}
