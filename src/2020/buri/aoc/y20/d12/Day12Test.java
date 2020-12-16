package buri.aoc.y20.d12;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day12Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(774, Day12.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(25, Day12.getResult(Part.ONE, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day12.getResult(Part.ONE, Day12.getInput(0));
		toConsole(result);
		assertEquals(2057, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(286, Day12.getResult(Part.TWO, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day12.getResult(Part.TWO, Day12.getInput(0));
		toConsole(result);
		assertEquals(71504, result);
	}
}
