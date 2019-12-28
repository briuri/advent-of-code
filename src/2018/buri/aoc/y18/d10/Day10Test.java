package buri.aoc.y18.d10;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(380, Day10.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		// HI
		String result = Day10.getResult(Part.ONE, Day10.getInput(1));
		toConsole(result);
		assertTrue(result.startsWith("#   #  ###\n"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		// PLBPGFRR
		String result = Day10.getResult(Part.ONE, Day10.getInput(0));
		toConsole(result);
		assertTrue(result.startsWith("#####   #       #####   #####    ####   ######  #####   ##### \n"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day10.getResult(Part.TWO, Day10.getInput(0));
		toConsole(result);
		assertEquals("10519", result);
	}
}
