package buri.aoc.y19.d02;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day02Test {

	@Test
	public void testGetInput() {
		List<Integer> input = Day02.getInput(0);
		assertEquals(157, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3500, Day02.getPart1Result(Day02.getInput(1), true));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day02.getPart1Result(Day02.getInput(0), false);
		System.out.println("Day 2 Part 1\n\t" + result);
		assertEquals(4930687, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day02.getPart2Result(Day02.getInput(0));
		System.out.println("Day 2 Part 2\n\t" + result);
		assertEquals(5335, result);
	}
}
