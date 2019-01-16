package buri.aoc.y15.d25;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;
import buri.aoc.data.Pair;

/**
 * @author Brian Uri!
 */
public class Day25Test {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day25.getResult(Part.ONE, new Pair(2947, 3029));
		System.out.println("Day 25 Part 1\n\t" + result);
		assertEquals(19980801, result);
	}
}
