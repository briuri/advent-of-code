package buri.aoc.y18.d21;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day21Test extends BaseTest {
	
	@Test
	public void testGetInput() {
		assertEquals(32, Day21.getInput(0).size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day21.getResult(Part.ONE, Day21.getInput(0));
		toConsole(result);
		assertEquals(16311888, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day21.getResult(Part.TWO, Day21.getInput(0));
		toConsole(result);
		assertEquals(1413889, result);
	}
}
