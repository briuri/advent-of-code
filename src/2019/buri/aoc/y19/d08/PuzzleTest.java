package buri.aoc.y19.d08;

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
		assertEquals(15000, Puzzle.getInput(0).length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("1", Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 3, 2));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 25, 6);
		toConsole(result);
		assertEquals("2684", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(" ■\n■ ", Puzzle.getResult(Part.TWO, Puzzle.getInput(2), 2, 2));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 25, 6);
		toConsole(result);
		// YGRYZ
		assertEquals(
			"■   ■ ■■  ■■■  ■   ■■■■■ \n■   ■■  ■ ■  ■ ■   ■   ■ \n ■ ■ ■    ■  ■  ■ ■   ■  \n  ■  ■ ■■ ■■■    ■   ■   \n  ■  ■  ■ ■ ■    ■  ■    \n  ■   ■■■ ■  ■   ■  ■■■■ ",
			result);
	}
}
