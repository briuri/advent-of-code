package buri.aoc.y20.d04;

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
		assertEquals(290, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(237, result);
	}

	@Test
	public void testPart2Examples() {
//		assertEquals(0, Day04.getResult(Part.TWO, Day04.getInput(2)));
		assertEquals(4, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(172, result);
	}
}
