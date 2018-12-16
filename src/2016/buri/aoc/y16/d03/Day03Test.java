package buri.aoc.y16.d03;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day03Test {

	@Test
	public void testGetInput() {
		List<String> input = Day03.getInput(0);
		assertEquals(1908, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day03.getResult(Part.ONE, Day03.getInput(0));
		System.out.println("Day 3 Part 1\n\t" + result);
		assertEquals(983, result);
	}
	
	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day03.getResult(Part.TWO, Day03.getInput(0));
		System.out.println("Day 3 Part 2\n\t" + result);
		assertEquals(0, result);
	}
}
