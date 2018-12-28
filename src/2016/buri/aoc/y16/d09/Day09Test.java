package buri.aoc.y16.d09;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day09Test {

	@Test
	public void testGetInput() {
		String input = Day09.getInput(0);
		assertEquals(16919, input.length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(6, Day09.getResult(Part.ONE, "ADVENT"));
		assertEquals(7, Day09.getResult(Part.ONE, "A(1x5)BC"));
		assertEquals(9, Day09.getResult(Part.ONE, "(3x3)XYZ"));
		assertEquals(11, Day09.getResult(Part.ONE, "A(2x2)BCD(2x2)EFG"));
		assertEquals(6, Day09.getResult(Part.ONE, "(6x1)(1x3)A"));
		assertEquals(18, Day09.getResult(Part.ONE, "X(8x2)(3x3)ABCY"));
	}

    
	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day09.getResult(Part.ONE, Day09.getInput(0));
		System.out.println("Day 9 Part 1\n\t" + result);
		assertEquals(120765, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(9, Day09.getResult(Part.TWO, "(3x3)XYZ"));
		assertEquals(20, Day09.getResult(Part.TWO, "X(8x2)(3x3)ABCY"));
		assertEquals(241920, Day09.getResult(Part.TWO, "(27x12)(20x12)(13x14)(7x10)(1x12)A"));
		assertEquals(445, Day09.getResult(Part.TWO, "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));
	}
    
	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day09.getResult(Part.TWO, Day09.getInput(0));
		System.out.println("Day 9 Part 2\n\t" + result);
		assertEquals(11658395076L, result);
	}
}
