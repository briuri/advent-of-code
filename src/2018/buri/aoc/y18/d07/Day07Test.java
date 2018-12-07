package buri.aoc.y18.d07;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day07Test {

	@Test
	public void testGetInput() {
		List<Data> input = Day07.getInput(0);
		assertEquals(0, input.size());
	}

	/**
	 * 
	 */
	@Test
	public void testPart1Examples() {
		assertEquals("", Day07.getResult(Part.ONE, Day07.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day07.getResult(Part.ONE, Day07.getInput(0));
		System.out.println("Day 7 Part 1\n\t" + result);
		assertEquals("", result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Examples() {
		assertEquals("", Day07.getResult(Part.TWO, Day07.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day07.getResult(Part.TWO, Day07.getInput(0));
		System.out.println("Day 7 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
