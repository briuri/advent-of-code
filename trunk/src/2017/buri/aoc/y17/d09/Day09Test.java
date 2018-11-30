package buri.aoc.y17.d09;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day09Test {

	@Test
	public void testGetInput() {
		String content = Day09.getInput(0);
		assertEquals(19135, content.length());
	}

	/**
	 * <>, empty garbage.
	 * <random characters>, garbage containing random characters.
	 * <<<<>, because the extra < are ignored.
	 * <{!>}>, because the first > is canceled.
	 * <!!>, because the second ! is canceled, allowing the > to terminate the garbage.
	 * <!!!>>, because the second ! and the first > are canceled.
	 * <{o"i!a,<{i<a>, which ends at the first >.
	 */
	@Test
	public void testPart1StripGarbageExamples() {
		// TODO
	}

	/**
	 * {}, 1 group.
	 * {{{}}}, 3 groups.
	 * {{},{}}, also 3 groups.
	 * {{{},{},{{}}}}, 6 groups.
	 * {<{},{},{{}}>}, 1 group (which itself contains garbage).
	 * {<a>,<a>,<a>,<a>}, 1 group.
	 * {{<a>},{<a>},{<a>},{<a>}}, 5 groups.
	 * {{<!>},{<!>},{<!>},{<a>}}, 2 groups (since all but the last > are canceled).
	 */
	@Test
	public void testPart1CountGroupsExamples() {
		// TODO
	}

	/**
	 * {}, score of 1.
	 * {{{}}}, score of 1 + 2 + 3 = 6.
	 * {{},{}}, score of 1 + 2 + 2 = 5.
	 * {{{},{},{{}}}}, score of 1 + 2 + 3 + 3 + 3 + 4 = 16.
	 * {<a>,<a>,<a>,<a>}, score of 1.
	 * {{<ab>},{<ab>},{<ab>},{<ab>}}, score of 1 + 2 + 2 + 2 + 2 = 9.
	 * {{<!!>},{<!!>},{<!!>},{<!!>}}, score of 1 + 2 + 2 + 2 + 2 = 9.
	 * {{<a!>},{<a!>},{<a!>},{<ab>}}, score of 1 + 2 = 3.
	 */
	@Test
	public void testPart1GetResultExamples() {
		// TODO
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day09.getResult(Part.ONE, Day09.getInput(0));
		System.out.println("Day 0 Part 1\n\t" + result);
		assertEquals(1, result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Example1() {
		// assertEquals(1, Day00.getResult(Part.TWO, "x"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day09.getResult(Part.TWO, Day09.getInput(0));
		System.out.println("Day 0 Part 2\n\t" + result);
		assertEquals(1, result);
	}
}
