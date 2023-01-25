package buri.aoc.y17.d07;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Example() {
		assertEquals("tknk", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("svugo", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("1152", result);
	}
}