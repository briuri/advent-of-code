package buri.aoc.y15.d16;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test {

	@Test
	public void testGetInput() {
		List<String> input = Day16.getInput(0);
		assertEquals(500, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day16.getResult(Part.ONE, Day16.getInput(0));
		System.out.println("Day 16 Part 1\n\t" + result);
		assertEquals(40, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day16.getResult(Part.TWO, Day16.getInput(0));
		System.out.println("Day 16 Part 2\n\t" + result);
		assertEquals(241, result);
	}
}