package buri.aoc.y15.d05;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day05Test {

	@Test
	public void testGetInput() {
		List<String> input = Day05.getInput(0);
		assertEquals(1000, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day05.getResult(Part.ONE, Day05.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day05.getResult(Part.ONE, Day05.getInput(0));
		System.out.println("Day 5 Part 1\n\t" + result);
		assertEquals(238, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day05.getResult(Part.TWO, Day05.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day05.getResult(Part.TWO, Day05.getInput(0));
		System.out.println("Day 5 Part 2\n\t" + result);
		assertEquals(69, result);
	}
}