package buri.aoc.y20.d07;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day07Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(594, Day07.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Day07.getResult(Part.ONE, Day07.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day07.getResult(Part.ONE, Day07.getInput(0));
		toConsole(result);
		assertEquals(289, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(32, Day07.getResult(Part.TWO, Day07.getInput(1)));
		assertEquals(126, Day07.getResult(Part.TWO, Day07.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day07.getResult(Part.TWO, Day07.getInput(0));
		toConsole(result);
		assertEquals(30055, result);
	}
}
