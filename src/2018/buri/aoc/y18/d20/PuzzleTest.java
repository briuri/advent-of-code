package buri.aoc.y18.d20;

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
		assertEquals(14052, Puzzle.getInput(0).length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(10, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(18, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(23, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(31, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3465, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(7956, result);
	}
}
