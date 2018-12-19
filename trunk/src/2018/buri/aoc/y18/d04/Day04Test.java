package buri.aoc.y18.d04;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * 
 * @author Brian Uri!
 */
public class Day04Test {

	@Test
	public void testGetInput() {
		List<Observation> input = Day04.getInput(0);
		assertEquals(905, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(240, Day04.getResult(Part.ONE, Day04.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, Day04.getInput(0));
		System.out.println("Day 4 Part 1\n\t" + result);
		assertEquals(76357, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4455, Day04.getResult(Part.TWO, Day04.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, Day04.getInput(0));
		System.out.println("Day 4 Part 2\n\t" + result);
		assertEquals(41668, result);
	}
}
