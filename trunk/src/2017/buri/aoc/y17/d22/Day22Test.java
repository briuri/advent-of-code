package buri.aoc.y17.d22;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day22Test {

	@Test
	public void testGetInput() {
		Cluster input = Day22.getInput(0);
		assertNotNull(input);
	}

	@Test
	public void testPart1Examples() {
		assertEquals(41, Day22.getResult(Part.ONE, Day22.getInput(1), 70));
		assertEquals(5587, Day22.getResult(Part.ONE, Day22.getInput(1), 10000));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day22.getResult(Part.ONE, Day22.getInput(0), 10000);
		System.out.println("Day 22 Part 1\n\t" + result);
		assertEquals(5196, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(26, Day22.getResult(Part.TWO, Day22.getInput(1), 100));
		assertEquals(2511944, Day22.getResult(Part.TWO, Day22.getInput(1), 10000000));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day22.getResult(Part.TWO, Day22.getInput(0), 10000000);
		System.out.println("Day 22 Part 2\n\t" + result);
		assertEquals(2511633, result);
	}
}