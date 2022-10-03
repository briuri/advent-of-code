package buri.aoc.y17.d10;

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
		assertEquals("12", Puzzle.getResult(Part.ONE, 5, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, 256, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("6909", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("33efeb34ea91902bb2f59c9920caa6cd", Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(2)));
		assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(3)));
		assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(4)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("9d5f4561367d379cfbf04f8c471c0095", result);
	}
}
