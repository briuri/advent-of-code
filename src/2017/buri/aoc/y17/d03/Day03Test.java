package buri.aoc.y17.d03;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day03Test {
	
	@Test
	public void testPart1Examples() {
		assertEquals(0, Day03.getResult(Part.ONE, 1));
		assertEquals(3, Day03.getResult(Part.ONE, 12));
		assertEquals(2, Day03.getResult(Part.ONE, 23));
		assertEquals(31, Day03.getResult(Part.ONE, 1024));
	}

	/**
	 * Solves the Part 1 puzzle against the real input.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day03.getResult(Part.ONE, 312051);
		System.out.println("Day 3 Part 1\n\t" + result);
		assertEquals(430, result);
	}
	
	@Test
	public void testPart2Examples() {
		assertEquals(747, Day03.getResult(Part.TWO, 700));
	}

	/**
	 * Solves the Part 2 puzzle against the real input.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day03.getResult(Part.TWO, 312051);
		System.out.println("Day 3 Part 2\n\t" + result);
		assertEquals(312453, result);
	}
}
