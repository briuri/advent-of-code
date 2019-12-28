package buri.aoc.y16.d15;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day15Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(6, Day15.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(5, Day15.getResult(Part.ONE, Day15.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day15.getResult(Part.ONE, Day15.getInput(0));
		toConsole(result);
		assertEquals(203660, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day15.getResult(Part.TWO, Day15.getInput(0));
		toConsole(result);
		assertEquals(2408135, result);
	}
}
