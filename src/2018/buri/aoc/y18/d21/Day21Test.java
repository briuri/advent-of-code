package buri.aoc.y18.d21;

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
		assertEquals(1, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(0, Day21.getResult(Part.ONE, Day21.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day21.getResult(Part.ONE, Day21.getInput(0));
		System.out.println("Day 21 Part 1\n\t" + result);
		assertEquals(0, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(0, Day21.getResult(Part.TWO, Day21.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day21.getResult(Part.TWO, Day21.getInput(0));
		System.out.println("Day 21 Part 2\n\t" + result);
		assertEquals(0, result);
	}
}
