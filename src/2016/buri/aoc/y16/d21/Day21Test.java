package buri.aoc.y16.d21;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day21Test {

	@Test
	public void testGetInput() {
		List<String> input = Day21.getInput(0);
		assertEquals(100, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("decab", Day21.getResult(Part.ONE, Day21.getInput(1), "abcde"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day21.getResult(Part.ONE, Day21.getInput(0), "abcdefgh");
		System.out.println("Day 21 Part 1\n\t" + result);
		assertEquals("agcebfdh", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day21.getResult(Part.TWO, Day21.getInput(0), "fbgdceah");
		System.out.println("Day 21 Part 2\n\t" + result);
		assertEquals("afhdbegc", result);
	}
}
