package buri.aoc.y20.d18;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day18Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(374, Day18.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(26335L, Day18.getResult(Part.ONE, Day18.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day18.getResult(Part.ONE, Day18.getInput(0));
		toConsole(result);
		assertEquals(1451467526514L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(693942L, Day18.getResult(Part.TWO, Day18.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day18.getResult(Part.TWO, Day18.getInput(0));
		toConsole(result);
		assertEquals(224973686321527L, result);
	}
}
