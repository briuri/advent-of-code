package buri.aoc.y17.d09;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1DestroyGarbageExamples() {
		assertEquals("", Puzzle.destroyGarbage("<>"));
		assertEquals("", Puzzle.destroyGarbage("<random characters>"));
		assertEquals("", Puzzle.destroyGarbage("<<<<>"));
		assertEquals("", Puzzle.destroyGarbage("<{!>}>"));
		assertEquals("", Puzzle.destroyGarbage("<!!>"));
		assertEquals("", Puzzle.destroyGarbage("<!!!>>"));
		assertEquals("", Puzzle.destroyGarbage("<{o\"i!a,<{i<a>"));
	}

	@Test
	public void testPart1CountGroupsExamples() {
		assertEquals(1, Puzzle.countGroups("{}"));
		assertEquals(3, Puzzle.countGroups("{{{}}}"));
		assertEquals(3, Puzzle.countGroups("{{},{}}"));
		assertEquals(6, Puzzle.countGroups("{{{},{},{{}}}}"));
		assertEquals(1, Puzzle.countGroups("{<{},{},{{}}>}"));
		assertEquals(1, Puzzle.countGroups("{<a>,<a>,<a>,<a>}"));
		assertEquals(5, Puzzle.countGroups("{{<a>},{<a>},{<a>},{<a>}}"));
		assertEquals(2, Puzzle.countGroups("{{<!>},{<!>},{<!>},{<a>}}"));
	}

	@Test
	public void testPart1GetResultExamples() {
		assertEquals(1, Puzzle.getResult(Part.ONE, "{}"));
		assertEquals(6, Puzzle.getResult(Part.ONE, "{{{}}}"));
		assertEquals(5, Puzzle.getResult(Part.ONE, "{{},{}}"));
		assertEquals(16, Puzzle.getResult(Part.ONE, "{{{},{},{{}}}}"));
		assertEquals(1, Puzzle.getResult(Part.ONE, "{<a>,<a>,<a>,<a>}"));
		assertEquals(9, Puzzle.getResult(Part.ONE, "{{<ab>},{<ab>},{<ab>},{<ab>}}"));
		assertEquals(9, Puzzle.getResult(Part.ONE, "{{<!!>},{<!!>},{<!!>},{<!!>}}"));
		assertEquals(3, Puzzle.getResult(Part.ONE, "{{<a!>},{<a!>},{<a!>},{<ab>}}"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(12396, result);
	}

	@Test
	public void testPart2GetResultExamples() {
		assertEquals(0, Puzzle.getResult(Part.TWO, "<>"));
		assertEquals(17, Puzzle.getResult(Part.TWO, "<random characters>"));
		assertEquals(3, Puzzle.getResult(Part.TWO, "<<<<>"));
		assertEquals(2, Puzzle.getResult(Part.TWO, "<{!>}>"));
		assertEquals(0, Puzzle.getResult(Part.TWO, "<!!>"));
		assertEquals(0, Puzzle.getResult(Part.TWO, "<!!!>>"));
		assertEquals(10, Puzzle.getResult(Part.TWO, "<{o\"i!a,<{i<a>"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(6346, result);
	}
}
