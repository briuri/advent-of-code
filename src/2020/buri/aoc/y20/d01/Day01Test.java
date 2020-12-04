package buri.aoc.y20.d01;

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
		assertEquals(200, Day01.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(514579, Day01.getResult(Part.ONE, Day01.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		toConsole(result);
		assertEquals(138379, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(241861950, Day01.getResult(Part.TWO, Day01.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		toConsole(result);
		assertEquals(85491920, result);
	}
}
