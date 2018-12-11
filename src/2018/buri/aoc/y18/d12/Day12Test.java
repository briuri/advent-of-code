package buri.aoc.y18.d12;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day12Test {

	@Test
	public void testGetInput() {
		List<String> input = Day12.getInput(0);
		assertEquals(1, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("", Day12.getResult(Part.ONE, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day12.getResult(Part.ONE, Day12.getInput(0));
		System.out.println("Day 12 Part 1\n\t" + result);
		assertEquals("", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("", Day12.getResult(Part.TWO, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day12.getResult(Part.TWO, Day12.getInput(0));
		System.out.println("Day 12 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
