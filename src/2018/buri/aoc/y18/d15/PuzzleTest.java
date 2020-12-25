package buri.aoc.y18.d15;

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
		assertEquals(32, Puzzle.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(27730, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(36334, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(39514, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(27755, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(28944, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
		assertEquals(18740, Puzzle.getResult(Part.ONE, Puzzle.getInput(6)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(190777, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4988, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(31284, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(3478, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(6474, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
		assertEquals(1140, Puzzle.getResult(Part.TWO, Puzzle.getInput(6)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(47388, result);
	}
}
