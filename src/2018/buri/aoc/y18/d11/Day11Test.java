package buri.aoc.y18.d11;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day11Test {

	@Test
	public void testPart1Examples() {
		assertEquals("33,45", Day11.getResult(Part.ONE, 18).toString());
		assertEquals("21,61", Day11.getResult(Part.ONE, 42).toString());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day11.getResult(Part.ONE, 7139);
		System.out.println("Day 11 Part 1\n\t" + result);
		assertEquals("20,62", result.toString());
	}

	@Test
	public void testPart2Examples() {
		assertEquals("90,269,16", Day11.getResult(Part.TWO, 18).toString());
		assertEquals("232,251,12", Day11.getResult(Part.TWO, 42).toString());
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day11.getResult(Part.TWO, 7139);
		System.out.println("Day 11 Part 2\n\t" + result);
		assertEquals("229,61,16", result);
	}
}