package buri.aoc.y16.d21;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(100, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("decab", Puzzle.getResult(Part.ONE, Puzzle.getInput(1), "abcde"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), "abcdefgh");
		toConsole(result);
		assertEquals("agcebfdh", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), "fbgdceah");
		toConsole(result);
		assertEquals("afhdbegc", result);
	}
}
