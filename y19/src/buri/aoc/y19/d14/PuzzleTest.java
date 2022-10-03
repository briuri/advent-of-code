package buri.aoc.y19.d14;

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
		assertEquals(31, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(165, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(13312, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(180697, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(2210736, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(399063, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(82892753, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(5586022, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(460664, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4215654, result);
	}
}
