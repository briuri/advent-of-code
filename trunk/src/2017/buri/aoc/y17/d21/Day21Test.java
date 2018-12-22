package buri.aoc.y17.d21;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day21Test {

	@Test
	public void testGetInput() {
		List<Rule> input = Day21.getInput(0);
		assertEquals(108, input.size());
	}
	
	@Test
	public void testPart1Examples() {
		assertEquals(12, Day21.getResult(Day21.getInput(1), 2));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day21.getResult(Day21.getInput(0), 5);
		System.out.println("Day 21 Part 1\n\t" + result);
		assertEquals(139, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day21.getResult(Day21.getInput(0), 18);
		System.out.println("Day 21 Part 2\n\t" + result);
		assertEquals(1857134, result);
	}
}