package buri.aoc.y18.d25;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day25Test {

	@Test
	public void testGetInput() {
		List<String> input = Day25.getInput(0);
		assertEquals(1305, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day25.getResult(Part.ONE, Day25.getInput(1)));
		assertEquals(1, Day25.getResult(Part.ONE, Day25.getInput(2)));
		assertEquals(4, Day25.getResult(Part.ONE, Day25.getInput(3)));
		assertEquals(3, Day25.getResult(Part.ONE, Day25.getInput(4)));
		assertEquals(8, Day25.getResult(Part.ONE, Day25.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day25.getResult(Part.ONE, Day25.getInput(0));
		System.out.println("Day 25 Part 1\n\t" + result);
		assertEquals(388, result);
	}
}
