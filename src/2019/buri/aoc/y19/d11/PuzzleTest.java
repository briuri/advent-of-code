package buri.aoc.y19.d11;

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
		assertEquals(613, Puzzle.getInput(0).size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("2319", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		// UERPRFGJ
		assertTrue(result.startsWith(" ■  ■ ■■■■ ■■■  ■■■  ■■■  ■■■■  ■■    ■■"));
	}
}
