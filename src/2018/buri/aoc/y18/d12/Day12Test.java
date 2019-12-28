package buri.aoc.y18.d12;

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
		assertEquals(33, Day12.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(325, Day12.getResult(Part.ONE, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day12.getResult(Part.ONE, Day12.getInput(0));
		toConsole(result);
		assertEquals(4386L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day12.getResult(Part.TWO, Day12.getInput(0));
		toConsole(result);
		assertEquals(5450000001166L, result);
	}
}
