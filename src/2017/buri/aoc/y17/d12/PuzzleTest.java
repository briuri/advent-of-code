package buri.aoc.y17.d12;

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
		assertEquals(6, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(134, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(193, result);
	}
}
