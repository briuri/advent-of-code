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
	
	/**
	 * These instructions would be processed as follows:
	 * 
	 * Because a starts at 0, it is not greater than 1, and so b is not modified.
	 * a is increased by 1 (to 1) because b is less than 5 (it is 0).
	 * c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
	 * c is increased by -20 (to -10) because c is equal to 10.
	 * After this process, the largest value in any register is 1.
	 */
	@Test
	public void testPart1Example() {
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

	/**
	 * For example, in the above instructions, the highest value ever held was 10 (in register c after the third
	 * instruction was evaluated).
	 */
	@Test
	public void testPart2Example() {
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
