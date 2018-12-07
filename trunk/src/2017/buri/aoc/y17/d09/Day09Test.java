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
