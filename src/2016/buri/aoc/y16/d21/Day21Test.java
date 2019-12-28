package buri.aoc.y16.d21;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day21Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(100, Day21.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("decab", Day21.getResult(Part.ONE, Day21.getInput(1), "abcde"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day21.getResult(Part.ONE, Day21.getInput(0), "abcdefgh");
		toConsole(result);
		assertEquals("agcebfdh", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day21.getResult(Part.TWO, Day21.getInput(0), "fbgdceah");
		toConsole(result);
		assertEquals("afhdbegc", result);
	}
}
