package buri.aoc.template;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day00Test {

	@Test
	public void getInput() {
//		String content = Day00.getInput(0);
//		assertEquals(2074, content.length());
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
		int result = Day00.getResult(Part.ONE, Day00.getInput(0));
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
		int result = Day00.getResult(Part.TWO, Day00.getInput(0));
		System.out.println("Day 0 Part 2\n\t" + result);
		assertEquals(1, result);
	}
}
