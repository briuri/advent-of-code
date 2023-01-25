package buri.aoc.y19.d20;

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
		assertEquals(23, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(58, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(684, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(26, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(396, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(7758, result);
	}
}