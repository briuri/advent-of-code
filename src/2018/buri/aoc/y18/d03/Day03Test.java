package buri.aoc.y18.d03;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day03Test {

	@Test
	public void testGetInput() {
		List<Claim> content = Day03.getInput(0);
		assertEquals(1349, content.size());
	}

	/**
	 * The problem is that many of the claims overlap, causing two or more claims to cover part of the same areas. For
	 * example, consider the following claims:
	 * 
	 * #1 @ 1,3: 4x4
	 * #2 @ 3,1: 4x4
	 * #3 @ 5,5: 2x2
	 * 
	 * Visually, these claim the following areas:
	 * 
	 * ........
	 * ...2222.
	 * ...2222.
	 * .11XX22.
	 * .11XX22.
	 * .111133.
	 * .111133.
	 * ........
	 * 
	 * The four square inches marked with X are claimed by both 1 and 2. (Claim 3, while adjacent to the others, does
	 * not overlap either of them.)
	 */
	@Test
	public void testPart1Examples() {
		assertEquals(4, Day03.getResult(Part.ONE, Day03.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day03.getResult(Part.ONE, Day03.getInput(0));
		System.out.println("Day 3 Part 1\n\t" + result);
		assertEquals(111266, result);
	}

	/**
	 * For example, in the claims above, only claim 3 is intact after all claims are made.
	 */
	@Test
	public void testPart2Examples() {
		assertEquals(3, Day03.getResult(Part.TWO, Day03.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day03.getResult(Part.TWO, Day03.getInput(0));
		System.out.println("Day 3 Part 2\n\t" + result);
		assertEquals(266, result);
	}
}
