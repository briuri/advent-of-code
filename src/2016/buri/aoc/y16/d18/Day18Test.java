package buri.aoc.y16.d18;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day18Test {

	@Test
	public void testGetInput() {
		String input = Day18.getInput(0);
		assertEquals(100, input.length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(38, Day18.getResult(Part.ONE, Day18.getInput(1), 10));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day18.getResult(Part.ONE, Day18.getInput(0), 40);
		System.out.println("Day 18 Part 1\n\t" + result);
		assertEquals(1989, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day18.getResult(Part.TWO, Day18.getInput(0), 400000);
		System.out.println("Day 18 Part 2\n\t" + result);
		assertEquals(19999894, result);
	}
}
