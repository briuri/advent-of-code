package buri.aoc.y18.d07;

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
		assertEquals("CABDFE", Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 1, 0));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 1, 0);
		toConsole(result);
		assertEquals("ABGKCMVWYDEHFOPQUILSTNZRJX", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("15", Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 2, 0));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 5, 60);
		toConsole(result);
		assertEquals("898", result);
	}
}
