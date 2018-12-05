package buri.aoc.y18.d05;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day05Test {

	@Test
	public void testGetInput() {
		String input = Day05.getInput(0);
		assertEquals(50000, input.length());
	}

	/**
	 * Now, consider a larger example, dabAcCaCBAcCcaDA:
	 * 
	 * dabAcCaCBAcCcaDA The first 'cC' is removed.
	 * dabAaCBAcCcaDA This creates 'Aa', which is removed.
	 * dabCBAcCcaDA Either 'cC' or 'Cc' are removed (the result is the same).
	 * dabCBAcaDA No further actions can be taken.
	 * 
	 * After all possible reactions, the resulting polymer contains 10 units.
	 */
	@Test
	public void testPart1Examples() {
		assertEquals(10, Day05.getResult(Part.ONE, "dabAcCaCBAcCcaDA"));
	}

	/**
	 * Real input failed because I wasn't backing index up far enough and it missed "pP".
	 */
	@Test
	public void testEarlyIndexBugCase() {
		assertEquals(1, Day05.getResult(Part.ONE, "pQqGgPz"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day05.getResult(Part.ONE, Day05.getInput(0));
		System.out.println("Day 5 Part 1\n\t" + result);
		assertEquals(9686, result);
	}

	/**
	 * For example, again using the polymer dabAcCaCBAcCcaDA from above:
	 * 
	 * Removing all A/a units produces dbcCCBcCcD. Fully reacting this polymer produces dbCBcD, which has length 6.
	 * Removing all B/b units produces daAcCaCAcCcaDA. Fully reacting this polymer produces daCAcaDA, which has length
	 * 8.
	 * Removing all C/c units produces dabAaBAaDA. Fully reacting this polymer produces daDA, which has length 4.
	 * Removing all D/d units produces abAcCaCBAcCcaA. Fully reacting this polymer produces abCBAc, which has length 6.
	 * 
	 * In this example, removing all C/c units was best, producing the answer 4.
	 */
	@Test
	public void testPart2Examples() {
		assertEquals(4, Day05.getResult(Part.TWO, "dabAcCaCBAcCcaDA"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day05.getResult(Part.TWO, Day05.getInput(0));
		System.out.println("Day 5 Part 2\n\t" + result);
		assertEquals(5524, result);
	}
}
