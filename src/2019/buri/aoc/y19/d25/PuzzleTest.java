package buri.aoc.y19.d25;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() throws IOException {
		String result = Puzzle.getResult(Puzzle.getInput(0));
		toConsole(result);
		assertEquals("134227456", result);
	}
}
