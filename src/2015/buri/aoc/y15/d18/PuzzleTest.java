package buri.aoc.y15.d18;

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
		assertEquals(100, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, 4, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 100, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(821, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(17, Puzzle.getResult(Part.TWO, 5, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 100, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(886, result);
	}
}
