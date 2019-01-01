package buri.aoc.y16.d20;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day20Test {

	@Test
	public void testGetInput() {
		List<Range> input = Day20.getInput(0);
		assertEquals(945, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day20.getResult(Part.ONE, Day20.getInput(1), 9L));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day20.getResult(Part.ONE, Day20.getInput(0), 4294967295L);
		System.out.println("Day 20 Part 1\n\t" + result);
		assertEquals(14975795, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day20.getResult(Part.TWO, Day20.getInput(1), 9L));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day20.getResult(Part.TWO, Day20.getInput(0), 4294967295L);
		System.out.println("Day 20 Part 2\n\t" + result);
		assertEquals(101, result);
	}
}
