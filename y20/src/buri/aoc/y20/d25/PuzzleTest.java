package buri.aoc.y20.d25;

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
		assertEquals(14897079L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 15));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 11900000);
		toConsole(result);
		assertEquals(10548634L, result);
	}
}
