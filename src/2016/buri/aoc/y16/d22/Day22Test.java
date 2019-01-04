package buri.aoc.y16.d22;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day22Test {

	@Test
	public void testGetInput() {
		List<String> input = Day22.getInput(0);
		assertEquals(990, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day22.getResult(Part.ONE, Day22.getInput(0));
		System.out.println("Day 22 Part 1\n\t" + result);
		assertEquals(967, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(7, Day22.getResult(Part.TWO, Day22.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day22.getResult(Part.TWO, Day22.getInput(0));
		System.out.println("Day 22 Part 2\n\t" + result);
		assertEquals(0, result);
	}
}
