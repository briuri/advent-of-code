package buri.aoc.y20.d10;

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
		assertEquals(35, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(220, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1890L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(19208, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(49607173328384L, result);
	}
}