package buri.aoc.y19.d01;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day01Test {

	@Test
	public void testGetInput() {
		List<Integer> input = Day01.getInput(0);
		assertEquals(100, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day01.getFuel(Part.ONE, 12));
		assertEquals(2, Day01.getFuel(Part.ONE, 14));
		assertEquals(654, Day01.getFuel(Part.ONE, 1969));
		assertEquals(33583, Day01.getFuel(Part.ONE, 100756));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		System.out.println("Day 1 Part 1\n\t" + result);
		assertEquals(3087896, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day01.getFuel(Part.TWO, 14));
		assertEquals(966, Day01.getFuel(Part.TWO, 1969));
		assertEquals(50346, Day01.getFuel(Part.TWO, 100756));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		System.out.println("Day 1 Part 2\n\t" + result);
		assertEquals(4628989, result);
	}
}
