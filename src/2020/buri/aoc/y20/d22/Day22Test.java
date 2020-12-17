package buri.aoc.y20.d22;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day22Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1, Day22.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(0, Day22.getResult(Part.ONE, Day22.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day22.getResult(Part.ONE, Day22.getInput(0));
		toConsole(result);
		assertEquals(0, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(0, Day22.getResult(Part.TWO, Day22.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day22.getResult(Part.TWO, Day22.getInput(0));
		toConsole(result);
		assertEquals(0, result);
	}
}
