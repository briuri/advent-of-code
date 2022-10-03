package buri.aoc.y21.d22;

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
		assertEquals("39", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals("590784", Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals("474140", Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("582644", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("2758514936282235", Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));

	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("1263804707062415", result);
	}
}
