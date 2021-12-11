package buri.aoc.y17.d16;

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
		assertEquals("baedc", Puzzle.getResult(Part.ONE, 5, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, 16, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("jcobhadfnmpkglie", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, 16, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("pclhmengojfdkaib", result);
	}
}
