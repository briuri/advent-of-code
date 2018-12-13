package buri.aoc.y18.d14;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day14Test {

	@Test
	public void testGetInput() {
		List<String> input = Day14.getInput(0);
		assertEquals(1, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("", Day14.getResult(Part.ONE, Day14.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day14.getResult(Part.ONE, Day14.getInput(0));
		System.out.println("Day 14 Part 1\n\t" + result);
		assertEquals("", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("", Day14.getResult(Part.TWO, Day14.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day14.getResult(Part.TWO, Day14.getInput(0));
		System.out.println("Day 14 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
