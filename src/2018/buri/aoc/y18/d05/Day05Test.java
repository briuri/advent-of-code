package buri.aoc.y18.d05;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day05Test {

	@Test
	public void testGetInput() {
		String input = Day05.getInput(0);
		assertEquals(50000, input.length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(10, Day05.getResult(Part.ONE, "dabAcCaCBAcCcaDA"));
	}

	/**
	 * Real input failed because I wasn't backing index up far enough and it missed "pP".
	 */
	@Test
	public void testEarlyIndexBugCase() {
		assertEquals(1, Day05.getResult(Part.ONE, "pQqGgPz"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day05.getResult(Part.ONE, Day05.getInput(0));
		System.out.println("Day 5 Part 1\n\t" + result);
		assertEquals(9686, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4, Day05.getResult(Part.TWO, "dabAcCaCBAcCcaDA"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day05.getResult(Part.TWO, Day05.getInput(0));
		System.out.println("Day 5 Part 2\n\t" + result);
		assertEquals(5524, result);
	}
}
