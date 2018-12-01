package buri.aoc.y18.d02;

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
		List<String> content = Day02.getInput(0);
		assertEquals(2074, content.size());
	}

	/**
	 * 
	 */
	@Test
	public void testPart1Examples() {
		assertEquals(1, Day02.getResult(Part.ONE, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day02.getResult(Part.ONE, Day02.getInput(0));
		System.out.println("Day 2 Part 1\n\t" + result);
		assertEquals(1, result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Examples() {
		assertEquals(1, Day02.getResult(Part.TWO, Day02.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day02.getResult(Part.TWO, Day02.getInput(0));
		System.out.println("Day 2 Part 2\n\t" + result);
		assertEquals(1, result);
	}
}
