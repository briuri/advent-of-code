package buri.aoc.y15.d01;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day01Test extends BaseTest {

	@Test
	public void testGetInput() {
		String input = Day01.getInput(0);
		assertEquals(7000, input.length());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		toConsole(result);
		assertEquals(74, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		toConsole(result);
		assertEquals(1795, result);
	}
}
