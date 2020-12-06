package buri.aoc.y20.d03;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day03Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(323, Day03.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(7, Day03.getResult(Part.ONE, Day03.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day03.getResult(Part.ONE, Day03.getInput(0));
		toConsole(result);
		assertEquals(167, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(336, Day03.getResult(Part.TWO, Day03.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day03.getResult(Part.TWO, Day03.getInput(0));
		toConsole(result);
		assertEquals(736527114, result);
	}
}
