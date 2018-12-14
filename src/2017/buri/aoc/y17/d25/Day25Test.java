package buri.aoc.y17.d25;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day25Test {

	@Test
	public void testGetInput() {
		Machine input = Day25.getInput(0);
		assertNotNull(input);
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day25.getResult(Day25.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day25.getResult(Day25.getInput(0));
		System.out.println("Day 25 Part 1\n\t" + result);
		assertEquals(3578, result);
	}
}
