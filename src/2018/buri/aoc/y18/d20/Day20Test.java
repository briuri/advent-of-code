package buri.aoc.y18.d20;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day20Test {

	@Test
	public void testGetInput() {
		String input = Day20.getInput(0);
		assertEquals(14052, input.length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day20.getResult(Part.ONE, Day20.getInput(1)));
		assertEquals(10, Day20.getResult(Part.ONE, Day20.getInput(2)));
		assertEquals(18, Day20.getResult(Part.ONE, Day20.getInput(3)));
		assertEquals(23, Day20.getResult(Part.ONE, Day20.getInput(4)));
		assertEquals(31, Day20.getResult(Part.ONE, Day20.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day20.getResult(Part.ONE, Day20.getInput(0));
		System.out.println("Day 20 Part 1\n\t" + result);
		assertEquals(3465, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day20.getResult(Part.TWO, Day20.getInput(0));
		System.out.println("Day 20 Part 2\n\t" + result);
		assertEquals(7956, result);
	}
}
