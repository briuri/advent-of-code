package buri.aoc.y16.d18;

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
		assertEquals(100, Puzzle.getInput(0).length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(38, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 10));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 40);
		toConsole(result);
		assertEquals(1989, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 400000);
		toConsole(result);
		assertEquals(19999894, result);
	}
}
