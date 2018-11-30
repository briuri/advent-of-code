package buri.aoc.y17.d09;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day09Test {

	@Test
	public void testGetInput() {
		String content = Day09.getInput(0);
		assertEquals(19135, content.length());
	}

	/**
	 * 
	 */
	@Test
	public void testPart1Example1() {
//		assertEquals(1, Day00.getResult(Part.ONE, "x"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day09.getResult(Part.ONE, Day09.getInput(0));
		System.out.println("Day 0 Part 1\n\t" + result);
		assertEquals(1, result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Example1() {
//		assertEquals(1, Day00.getResult(Part.TWO, "x"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day09.getResult(Part.TWO, Day09.getInput(0));
		System.out.println("Day 0 Part 2\n\t" + result);
		assertEquals(1, result);
	}
}
