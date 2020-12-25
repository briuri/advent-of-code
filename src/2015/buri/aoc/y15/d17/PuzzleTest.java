package buri.aoc.y15.d17;

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
		assertEquals(20, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, 25, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 150, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1638, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Puzzle.getResult(Part.TWO, 25, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 150, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(17, result);
	}
}
