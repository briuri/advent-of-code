package buri.aoc.y16.d01;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day01Test {

	@Test
	public void testGetInput() {
		List<String> input = Day01.getInput(0);
		assertEquals(170, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(5, Day01.getResult(Part.ONE, Day01.getInput(1)));
		assertEquals(2, Day01.getResult(Part.ONE, Day01.getInput(2)));
		assertEquals(12, Day01.getResult(Part.ONE, Day01.getInput(3)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		System.out.println("Day 1 Part 1\n\t" + result);
		assertEquals(307, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4, Day01.getResult(Part.TWO, Day01.getInput(4)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		System.out.println("Day 1 Part 2\n\t" + result);
		assertEquals(165, result);
	}
}
