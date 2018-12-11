package buri.aoc.y17.d08;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day08Test {
	
	@Test
	public void testGetInput() {
		List<RegisterInstruction> instructions = Day08.getInput(0);
		assertEquals(1000, instructions.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(1, Day08.getResult(Part.ONE, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day08.getResult(Part.ONE, Day08.getInput(0));
		System.out.println("Day 8 Part 1\n\t" + result);
		assertEquals(4888, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(10, Day08.getResult(Part.TWO, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day08.getResult(Part.TWO, Day08.getInput(0));
		System.out.println("Day 8 Part 2\n\t" + result);
		assertEquals(7774, result);
	}
	
}