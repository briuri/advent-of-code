package buri.aoc.y20.d13;

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
		assertEquals(295, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2845L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1068781L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(754018L, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
		assertEquals(779210L, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(1261476L, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(1202161486L, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(487905974205117L, result);
	}
}