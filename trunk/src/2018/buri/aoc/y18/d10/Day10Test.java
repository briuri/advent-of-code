package buri.aoc.y18.d10;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test {

	@Test
	public void testGetInput() {
		List<Position> input = Day10.getInput(0);
		assertEquals(380, input.size());
	}

	@Test
	public void testPart1Examples() {
		// HI
		String result = Day10.getResult(Part.ONE, Day10.getInput(1));
		assertTrue(result.startsWith("#   #  ###\n"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		// PLBPGFRR
		String result = Day10.getResult(Part.ONE, Day10.getInput(0));
		System.out.println("Day 10 Part 1\n" + result);
		assertTrue(result.startsWith("#####   #       #####   #####    ####   ######  #####   ##### \n"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day10.getResult(Part.TWO, Day10.getInput(0));
		System.out.println("Day 10 Part 2\n\t" + result);
		assertEquals("10519", result);
	}
}
