package buri.aoc.y18.d01;

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
		List<String> content = Day01.getInput(0);
		assertEquals(2074, content.size());
	}

	/**
	 * 
	 */
	@Test
	public void testPart1Example1() {
		assertEquals(1, Day01.getResult(Part.ONE, Day01.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		System.out.println("Day 1 Part 1\n\t" + result);
		assertEquals(1, result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Example1() {
		assertEquals(1, Day01.getResult(Part.TWO, Day01.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		System.out.println("Day 1 Part 2\n\t" + result);
		assertEquals(1, result);
	}
}
