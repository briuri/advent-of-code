package buri.aoc.y18.d25;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1305, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Puzzle.getResult(Puzzle.getInput(1)));
		assertEquals(1, Puzzle.getResult(Puzzle.getInput(2)));
		assertEquals(4, Puzzle.getResult(Puzzle.getInput(3)));
		assertEquals(3, Puzzle.getResult(Puzzle.getInput(4)));
		assertEquals(8, Puzzle.getResult(Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(388, result);
	}
}
