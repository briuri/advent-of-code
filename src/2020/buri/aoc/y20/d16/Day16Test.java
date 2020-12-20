package buri.aoc.y20.d16;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(257, Day16.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(71L, Day16.getResult(Part.ONE, Day16.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day16.getResult(Part.ONE, Day16.getInput(0));
		toConsole(result);
		assertEquals(21978L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day16.getResult(Part.TWO, Day16.getInput(0));
		toConsole(result);
		assertEquals(1053686852011L, result);
	}
}
