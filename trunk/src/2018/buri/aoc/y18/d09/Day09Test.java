package buri.aoc.y18.d09;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day09Test {

	@Test
	public void testGetInput() {
		List<String> input = Day09.getInput(0);
		assertEquals(0, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("", Day09.getResult(Part.ONE, Day09.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day09.getResult(Part.ONE, Day09.getInput(0));
		System.out.println("Day 9 Part 1\n\t" + result);
		assertEquals("", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("", Day09.getResult(Part.TWO, Day09.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day09.getResult(Part.TWO, Day09.getInput(0));
		System.out.println("Day 9 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
