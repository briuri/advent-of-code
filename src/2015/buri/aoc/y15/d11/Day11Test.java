package buri.aoc.y15.d11;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day11Test {
	
	@Test
	public void testPart1Examples() {
		assertEquals("abcdffaa", Day11.getResult(Part.ONE, "abcdefgh"));
		assertEquals("ghjaabcc", Day11.getResult(Part.ONE, "ghijklmn"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day11.getResult(Part.ONE, "hepxcrrq");
		System.out.println("Day 11 Part 1\n\t" + result);
		assertEquals("hepxxyzz", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day11.getResult(Part.TWO, "hepxxyzz");
		System.out.println("Day 11 Part 2\n\t" + result);
		assertEquals("heqaabcc", result);
	}
}
