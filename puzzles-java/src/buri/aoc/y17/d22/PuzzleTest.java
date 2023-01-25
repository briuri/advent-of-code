package buri.aoc.y17.d22;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(41, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 70));
		assertEquals(5587, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 10000));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 10000);
		toConsole(result);
		assertEquals(5196, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(26, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 100));
		assertEquals(2511944, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 10000000));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 10000000);
		toConsole(result);
		assertEquals(2511633, result);
	}
}