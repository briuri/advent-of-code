package buri.aoc.y16.d15;

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
		List<Disc> input = Day15.getInput(0);
		assertEquals(6, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(5, Day15.getResult(Part.ONE, Day15.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day15.getResult(Part.ONE, Day15.getInput(0));
		System.out.println("Day 15 Part 1\n\t" + result);
		assertEquals(203660, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day15.getResult(Part.TWO, Day15.getInput(0));
		System.out.println("Day 15 Part 2\n\t" + result);
		assertEquals(2408135, result);
	}
}
