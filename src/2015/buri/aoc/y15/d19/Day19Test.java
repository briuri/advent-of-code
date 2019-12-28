package buri.aoc.y15.d19;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day19Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(44, Day19.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Day19.getResult(Part.ONE, Day19.getInput(1)));
		assertEquals(7, Day19.getResult(Part.ONE, Day19.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day19.getResult(Part.ONE, Day19.getInput(0));
		toConsole(result);
		assertEquals(576, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Day19.getResult(Part.TWO, Day19.getInput(3)));
		assertEquals(6, Day19.getResult(Part.TWO, Day19.getInput(4)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day19.getResult(Part.TWO, Day19.getInput(0));
		toConsole(result);
		assertEquals(207, result);
	}
}
