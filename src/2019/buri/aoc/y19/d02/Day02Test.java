package buri.aoc.y19.d02;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class Day02Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day02.getInput(0);
		assertEquals(157, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3500L, Day02.getPart1Result(Day02.getInput(1), true));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day02.getPart1Result(Day02.getInput(0), false);
		toClipboard(result);
		System.out.println("Day 2 Part 1\n\t" + result);
		assertEquals(4930687L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day02.getPart2Result(Day02.getInput(0));
		toClipboard(result);
		System.out.println("Day 2 Part 2\n\t" + result);
		assertEquals(5335, result);
	}
}
