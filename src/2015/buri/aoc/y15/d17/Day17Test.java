package buri.aoc.y15.d17;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day17Test {

	@Test
	public void testGetInput() {
		List<String> input = Day17.getInput(0);
		assertEquals(20, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Day17.getResult(Part.ONE, 25, Day17.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day17.getResult(Part.ONE, 150, Day17.getInput(0));
		System.out.println("Day 17 Part 1\n\t" + result);
		assertEquals(0, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Day17.getResult(Part.TWO, 25, Day17.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day17.getResult(Part.TWO, 150, Day17.getInput(0));
		System.out.println("Day 17 Part 2\n\t" + result);
		assertEquals(0, result);
	}
}
