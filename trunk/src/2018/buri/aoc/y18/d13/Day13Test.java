package buri.aoc.y18.d13;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day13Test {

	@Test
	public void testGetInput() {
		Tracks input = Day13.getInput(0);
		assertNotNull(input);
	}

	@Test
	public void testPart1Examples() {
		assertEquals("7,3", Day13.getResult(Part.ONE, Day13.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day13.getResult(Part.ONE, Day13.getInput(0));
		System.out.println("Day 13 Part 1\n\t" + result);
		assertEquals("50,54", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("6,4", Day13.getResult(Part.TWO, Day13.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day13.getResult(Part.TWO, Day13.getInput(0));
		System.out.println("Day 13 Part 2\n\t" + result);
		assertEquals("50,100", result);
	}
}
