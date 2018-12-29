package buri.aoc.y16.d10;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test {

	@Test
	public void testGetInput() {
		List<String> input = Day10.getInput(0);
		assertEquals(231, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day10.getResult(Part.ONE, Day10.getInput(0));
		System.out.println("Day 10 Part 1\n\t" + result);
		assertEquals(113, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day10.getResult(Part.TWO, Day10.getInput(0));
		System.out.println("Day 10 Part 2\n\t" + result);
		// Not zero.
		assertEquals(0, result);
	}
}
