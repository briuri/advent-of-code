package buri.aoc.y17.d24;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day24Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(57, Day24.getInput(0).getSize());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(31, Day24.getResult(Part.ONE, Day24.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day24.getResult(Part.ONE, Day24.getInput(0));
		toConsole(result);
		assertEquals(2006, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(19, Day24.getResult(Part.TWO, Day24.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day24.getResult(Part.TWO, Day24.getInput(0));
		toConsole(result);
		assertEquals(1994, result);
	}
}
