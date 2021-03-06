package buri.aoc.y15.d19;

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
		assertEquals(44, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(7, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(576, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(6, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(207, result);
	}
}
