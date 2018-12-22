package buri.aoc.y18.d22;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;
import buri.aoc.data.Pair;

/**
 * @author Brian Uri!
 */
public class Day22Test {

	@Test
	public void testPart1Examples() {
		assertEquals(114, Day22.getResult(Part.ONE, 510, new Pair(10, 10)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day22.getResult(Part.ONE, 3879, new Pair(8, 713));
		System.out.println("Day 22 Part 1\n\t" + result);
		assertEquals(6323, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(45, Day22.getResult(Part.TWO, 510, new Pair(10, 10)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day22.getResult(Part.TWO, 3879, new Pair(8, 713));
		System.out.println("Day 22 Part 2\n\t" + result);
		assertEquals(982, result);
	}
}
