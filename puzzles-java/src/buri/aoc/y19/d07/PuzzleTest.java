package buri.aoc.y19.d07;

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
		assertEquals(43210L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(54321L, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(65210L, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(422858L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(139629729L, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(18216L, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(14897241L, result);
	}
}