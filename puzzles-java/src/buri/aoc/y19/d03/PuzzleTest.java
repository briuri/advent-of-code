package buri.aoc.y19.d03;

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
		assertEquals(6, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 20));
		assertEquals(159, Puzzle.getResult(Part.ONE, Puzzle.getInput(2), 500));
		assertEquals(135, Puzzle.getResult(Part.ONE, Puzzle.getInput(3), 400));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 16000);
		toConsole(result);
		assertEquals(731, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(30, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 20));
		assertEquals(610, Puzzle.getResult(Part.TWO, Puzzle.getInput(2), 500));
		assertEquals(410, Puzzle.getResult(Part.TWO, Puzzle.getInput(3), 400));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 16000);
		toConsole(result);
		assertEquals(5672, result);
	}
}