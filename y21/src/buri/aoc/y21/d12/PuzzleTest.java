package buri.aoc.y21.d12;

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
		assertEquals(10L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(19L, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(226L, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3000L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(36L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(103L, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
		assertEquals(3509L, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(74222L, result);
	}
}
