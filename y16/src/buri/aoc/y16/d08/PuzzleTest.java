package buri.aoc.y16.d08;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("116", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		// Visual inspection: UPOJFLBCEZ
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertTrue(result.startsWith("■  ■ ■■■   ■■    ■■ ■■■■ ■    ■■■   ■■  ■■■■ ■■■■"));
	}
}
