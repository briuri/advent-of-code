package buri.aoc.y16.d13;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;
import buri.aoc.data.Pair;

/**
 * @author Brian Uri!
 */
public class Day13Test {

	@Test
	public void testPart1Examples() {
		assertEquals(11, Day13.getResult(Part.ONE, 10, new Pair(7, 4)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day13.getResult(Part.ONE, 1352, new Pair(31, 39));
		System.out.println("Day 13 Part 1\n\t" + result);
		assertEquals(90, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day13.getResult(Part.TWO, 1352, new Pair(31, 39));
		System.out.println("Day 13 Part 2\n\t" + result);
		assertEquals(135, result);
	}
}
