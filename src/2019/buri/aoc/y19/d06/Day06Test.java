package buri.aoc.y19.d06;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day06Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1805, Day06.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(42, Day06.getResult(Part.ONE, Day06.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day06.getResult(Part.ONE, Day06.getInput(0));
		toConsole(result);
		assertEquals(314702, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4, Day06.getResult(Part.TWO, Day06.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day06.getResult(Part.TWO, Day06.getInput(0));
		toConsole(result);
		assertEquals(439, result);
	}
}
