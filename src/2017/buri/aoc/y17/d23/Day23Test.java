package buri.aoc.y17.d23;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day23Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(32, Day23.getInput(0).size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day23.getResult(Part.ONE, Day23.getInput(0));
		toConsole(result);
		assertEquals(3025, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day23.getResult(Part.TWO, Day23.getInput(0));
		toConsole(result);
		assertEquals(915, result);
	}
}
