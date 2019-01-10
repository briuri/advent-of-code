package buri.aoc.y16.d11;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day11Test {

	@Test
	public void testGetInput() {
		String input = Day11.getInput(0);
		assertEquals(16, input.length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(11, Day11.getResult(Part.ONE, Day11.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day11.getResult(Part.ONE, Day11.getInput(0));
		System.out.println("Day 11 Part 1\n\t" + result);
		assertEquals(31, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day11.getResult(Part.TWO, Day11.getInput(2));
		System.out.println("Day 11 Part 2\n\t" + result);
		assertEquals(55, result);
	}
}
