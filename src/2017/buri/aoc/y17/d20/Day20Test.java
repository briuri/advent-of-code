package buri.aoc.y17.d20;

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
		List<Particle> input = Day20.getInput(0);
		assertEquals(1000, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(0, Day20.getResult(Part.ONE, Day20.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day20.getResult(Part.ONE, Day20.getInput(0));
		System.out.println("Day 20 Part 1\n\t" + result);
		assertEquals(364, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1, Day20.getResult(Part.TWO, Day20.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day20.getResult(Part.TWO, Day20.getInput(0));
		System.out.println("Day 20 Part 2\n\t" + result);
		assertEquals(420, result);
	}
}
