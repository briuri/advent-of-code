package buri.aoc.template;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day00Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1, Day00.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(0L, Day00.getResult(Part.ONE, Day00.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day00.getResult(Part.ONE, Day00.getInput(0));
		toConsole(result);
		assertEquals(0L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(0L, Day00.getResult(Part.TWO, Day00.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day00.getResult(Part.TWO, Day00.getInput(0));
		toConsole(result);
		assertEquals(0L, result);
	}
}
