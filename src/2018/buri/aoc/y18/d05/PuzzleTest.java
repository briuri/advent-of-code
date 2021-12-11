package buri.aoc.y18.d05;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(10, Puzzle.getResult(Part.ONE, "dabAcCaCBAcCcaDA"));
	}

	/**
	 * Real input failed because I wasn't backing index up far enough and it missed "pP".
	 */
	@Test
	public void testEarlyIndexBugCase() {
		assertEquals(1, Puzzle.getResult(Part.ONE, "pQqGgPz"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(9686, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4, Puzzle.getResult(Part.TWO, "dabAcCaCBAcCcaDA"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(5524, result);
	}
}
