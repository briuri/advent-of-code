package buri.aoc.y16.d20;

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
		assertEquals(3, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 9L));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 4294967295L);
		toConsole(result);
		assertEquals(14975795, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 9L));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 4294967295L);
		toConsole(result);
		assertEquals(101, result);
	}
}