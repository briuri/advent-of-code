package buri.aoc.y18.d15;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day15Test {

	@Test
	public void testGetInput() {
		List<String> input = Day15.getInput(0);
		assertEquals(1, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("", Day15.getResult(Part.ONE, Day15.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day15.getResult(Part.ONE, Day15.getInput(0));
		System.out.println("Day 0 Part 1\n\t" + result);
		assertEquals("", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("", Day15.getResult(Part.TWO, Day15.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day15.getResult(Part.TWO, Day15.getInput(0));
		System.out.println("Day 0 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
