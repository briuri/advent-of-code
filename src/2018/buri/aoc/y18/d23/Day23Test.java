package buri.aoc.y18.d23;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day23Test {

	@Test
	public void testGetInput() {
		List<Bot> input = Day23.getInput(0);
		assertEquals(1000, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(7, Day23.getResult(Part.ONE, Day23.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day23.getResult(Part.ONE, Day23.getInput(0));
		System.out.println("Day 23 Part 1\n\t" + result);
		assertEquals(691, result);
	}
	
	@Test
	public void testPart2Examples() {
		assertEquals(36, Day23.getResult(Part.TWO, Day23.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day23.getResult(Part.TWO, Day23.getInput(0));
		System.out.println("Day 23 Part 2\n\t" + result);
		assertEquals(126529978, result);
	}
}
