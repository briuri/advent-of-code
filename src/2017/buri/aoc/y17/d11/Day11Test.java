package buri.aoc.y17.d11;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day11Test {

	@Test
	public void testGetInput() {
		List<String> input = Day11.getInput(0);
		assertEquals(8223, input.size());
	}

	/**
	 * 
	 */
	@Test
	public void testPart1Examples() {
		assertEquals("3", Day11.getResult(Part.ONE, Day11.getInput(1)));
		assertEquals("0", Day11.getResult(Part.ONE, Day11.getInput(2)));
		assertEquals("2", Day11.getResult(Part.ONE, Day11.getInput(3)));
		assertEquals("3", Day11.getResult(Part.ONE, Day11.getInput(4)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day11.getResult(Part.ONE, Day11.getInput(0));
		System.out.println("Day 11 Part 1\n\t" + result);
		assertEquals("", result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Examples() {
		//assertEquals("", Day11.getResult(Part.TWO, Day11.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day11.getResult(Part.TWO, Day11.getInput(0));
		System.out.println("Day 11 Part 2\n\t" + result);
		assertEquals("", result);
	}
}
