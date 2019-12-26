package buri.aoc.y15.d12;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day12Test extends BaseTest {

	@Test
	public void testGetInput() {
		String input = Day12.getInput(0);
		assertEquals(26663, input.length());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day12.getResult(Part.ONE, Day12.getInput(0));
		toConsole(result);
		assertEquals(111754, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day12.getResult(Part.TWO, Day12.getInput(0));
		toConsole(result);
		assertEquals(65402, result);
	}
}
