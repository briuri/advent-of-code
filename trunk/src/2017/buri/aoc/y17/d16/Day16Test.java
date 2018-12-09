package buri.aoc.y17.d16;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test {

	@Test
	public void testGetInput() {
		List<String> input = Day16.getInput(0);
		assertEquals(10000, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("baedc", Day16.getResult(Part.ONE, 5, Day16.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day16.getResult(Part.ONE, 16, Day16.getInput(0));
		System.out.println("Day 16 Part 1\n\t" + result);
		assertEquals("jcobhadfnmpkglie", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day16.getResult(Part.TWO, 16, Day16.getInput(0));
		System.out.println("Day 16 Part 2\n\t" + result);
		assertEquals("pclhmengojfdkaib", result);
	}
}
