package buri.aoc.y15.d21;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day21Test {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day21.getResult(Part.ONE, new Unit(103, 9, 2, 0));
		System.out.println("Day 21 Part 1\n\t" + result);
		assertEquals(121, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day21.getResult(Part.TWO, new Unit(103, 9, 2, 0));
		System.out.println("Day 21 Part 2\n\t" + result);
		assertEquals(201, result);
	}
}
