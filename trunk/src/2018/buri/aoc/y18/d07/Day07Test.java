package buri.aoc.y18.d07;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day07Test {

	@Test
	public void testGetInput() {
		List<String> input = Day07.getInput(0);
		assertEquals(101, input.size());
	}

	/**
	 * 
	 */
	@Test
	public void testPart1Examples() {
		assertEquals("CABDFE", Day07.getPart1Result(Day07.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day07.getPart1Result(Day07.getInput(0));
		System.out.println("Day 7 Part 1\n\t" + result);
		assertEquals("ABGKCMVWYDEHFOPQUILSTNZRJX", result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Examples() {
		assertEquals(15, Day07.getPart2Result(Day07.getInput(1), 2, 0));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day07.getPart2Result(Day07.getInput(0), 5, 60);
		System.out.println("Day 7 Part 2\n\t" + result);
		assertEquals(898, result);
	}
}
