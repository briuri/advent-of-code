package buri.aoc.y20.d02;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day02Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1000, Day02.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day02.getResult(Part.ONE, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day02.getResult(Part.ONE, Day02.getInput(0));
		toConsole(result);
		assertEquals(628, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1, Day02.getResult(Part.TWO, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day02.getResult(Part.TWO, Day02.getInput(0));
		toConsole(result);
		assertEquals(705, result);
	}
}
