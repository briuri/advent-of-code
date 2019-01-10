package buri.aoc.y15.d02;

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
		assertEquals(1000, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day02.getResult(Part.ONE, Day02.getInput(0));
		System.out.println("Day 2 Part 1\n\t" + result);
		assertEquals(1588178, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day02.getResult(Part.TWO, Day02.getInput(0));
		System.out.println("Day 2 Part 2\n\t" + result);
		assertEquals(3783758, result);
	}
}
