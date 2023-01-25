package buri.aoc.y22.d10;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals("13140", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("13680", result);
	}

	@Test
	public void testPart2Examples() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(1));
		assertTrue(result.startsWith("■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  "));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		// Visual inspection: PZGPKPEB
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertTrue(result.startsWith("■■■  ■■■■  ■■  ■■■  ■  ■ ■■■  ■■■■ ■■■  "));
	}
}