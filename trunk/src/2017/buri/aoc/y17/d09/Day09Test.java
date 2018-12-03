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
		String input = Day09.getInput(0);
		assertEquals(19135, input.length());
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
	public void testPart1DestroyGarbageExamples() {
		assertEquals("", Day09.destroyGarbage("<>"));
		assertEquals("", Day09.destroyGarbage("<random characters>"));
		assertEquals("", Day09.destroyGarbage("<<<<>"));
		assertEquals("", Day09.destroyGarbage("<{!>}>"));
		assertEquals("", Day09.destroyGarbage("<!!>"));
		assertEquals("", Day09.destroyGarbage("<!!!>>"));
		assertEquals("", Day09.destroyGarbage("<{o\"i!a,<{i<a>"));
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
		assertEquals(1, Day09.countGroups("{}"));
		assertEquals(3, Day09.countGroups("{{{}}}"));
		assertEquals(3, Day09.countGroups("{{},{}}"));
		assertEquals(6, Day09.countGroups("{{{},{},{{}}}}"));
		assertEquals(1, Day09.countGroups("{<{},{},{{}}>}"));
		assertEquals(1, Day09.countGroups("{<a>,<a>,<a>,<a>}"));
		assertEquals(5, Day09.countGroups("{{<a>},{<a>},{<a>},{<a>}}"));
		assertEquals(2, Day09.countGroups("{{<!>},{<!>},{<!>},{<a>}}"));
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
		assertEquals(1, Day09.getResult(Part.ONE, "{}"));
		assertEquals(6, Day09.getResult(Part.ONE, "{{{}}}"));
		assertEquals(5, Day09.getResult(Part.ONE, "{{},{}}"));
		assertEquals(16, Day09.getResult(Part.ONE, "{{{},{},{{}}}}"));
		assertEquals(1, Day09.getResult(Part.ONE, "{<a>,<a>,<a>,<a>}"));
		assertEquals(9, Day09.getResult(Part.ONE, "{{<ab>},{<ab>},{<ab>},{<ab>}}"));
		assertEquals(9, Day09.getResult(Part.ONE, "{{<!!>},{<!!>},{<!!>},{<!!>}}"));
		assertEquals(3, Day09.getResult(Part.ONE, "{{<a!>},{<a!>},{<a!>},{<ab>}}"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day09.getResult(Part.ONE, Day09.getInput(0));
		System.out.println("Day 0 Part 1\n\t" + result);
		assertEquals(12396, result);
	}

	/**
	 * To prove you've removed it, you need to count all of the characters within the garbage. The leading and trailing
	 * < and > don't count, nor do any canceled characters or the ! doing the canceling.
	 * 
	 * <>, 0 characters.
	 * <random characters>, 17 characters.
	 * <<<<>, 3 characters.
	 * <{!>}>, 2 characters.
	 * <!!>, 0 characters.
	 * <!!!>>, 0 characters.
	 * <{o"i!a,<{i<a>, 10 characters.
	 */
	@Test
	public void testPart2GetResultExamples() {
		assertEquals(0, Day09.getResult(Part.TWO, "<>"));
		assertEquals(17, Day09.getResult(Part.TWO, "<random characters>"));
		assertEquals(3, Day09.getResult(Part.TWO, "<<<<>"));
		assertEquals(2, Day09.getResult(Part.TWO, "<{!>}>"));
		assertEquals(0, Day09.getResult(Part.TWO, "<!!>"));
		assertEquals(0, Day09.getResult(Part.TWO, "<!!!>>"));
		assertEquals(10, Day09.getResult(Part.TWO, "<{o\"i!a,<{i<a>"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day09.getResult(Part.TWO, Day09.getInput(0));
		System.out.println("Day 9 Part 2\n\t" + result);
		assertEquals(6346, result);
	}
}
