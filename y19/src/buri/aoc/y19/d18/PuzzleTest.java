package buri.aoc.y19.d18;

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
		assertEquals(86, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(132, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(136, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(81, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5392, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8, Puzzle.getResult(Part.TWO, Puzzle.getInput(7)));
		assertEquals(24, Puzzle.getResult(Part.TWO, Puzzle.getInput(8)));
		assertEquals(32, Puzzle.getResult(Part.TWO, Puzzle.getInput(9)));
		// assertEquals(72, Day18.getResult(Part.TWO, Day18.getInput(10)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(6));
		toConsole(result);
		assertEquals(1684, result);
	}
}
