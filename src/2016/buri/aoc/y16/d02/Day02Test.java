package buri.aoc.y16.d02;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day02Test {

	@Test
	public void testGetInput() {
		List<String> input = Day02.getInput(0);
		assertEquals(5, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("1985", Day02.getResult(Part.ONE, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day02.getResult(Part.ONE, Day02.getInput(0));
		System.out.println("Day 0 Part 1\n\t" + result);
		assertEquals("12578", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("5DB3", Day02.getResult(Part.TWO, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day02.getResult(Part.TWO, Day02.getInput(0));
		System.out.println("Day 0 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
