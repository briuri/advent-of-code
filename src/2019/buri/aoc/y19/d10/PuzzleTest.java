package buri.aoc.y19.d10;

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
		assertEquals(8, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(33, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(35, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(41, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(210, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(230, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(802, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1205, result);
	}
}
