package buri.aoc.y18.d18;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day18Test {

	@Test
	public void testGetInput() {
		List<String> input = Day18.getInput(0);
		assertEquals(1, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("", Day18.getResult(Part.ONE, Day18.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day18.getResult(Part.ONE, Day18.getInput(0));
		System.out.println("Day 18 Part 1\n\t" + result);
		assertEquals("", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("", Day18.getResult(Part.TWO, Day18.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day18.getResult(Part.TWO, Day18.getInput(0));
		System.out.println("Day 18 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
