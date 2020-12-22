package buri.aoc.y20.d17;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day17Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(8, Day17.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(112, Day17.getResult(Part.ONE, Day17.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day17.getResult(Part.ONE, Day17.getInput(0));
		toConsole(result);
		assertEquals(223, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(848, Day17.getResult(Part.TWO, Day17.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day17.getResult(Part.TWO, Day17.getInput(0));
		toConsole(result);
		assertEquals(1884, result);
	}
}
