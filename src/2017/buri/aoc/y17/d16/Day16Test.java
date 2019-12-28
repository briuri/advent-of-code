package buri.aoc.y17.d16;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(10000, Day16.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("baedc", Day16.getResult(Part.ONE, 5, Day16.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day16.getResult(Part.ONE, 16, Day16.getInput(0));
		toConsole(result);
		assertEquals("jcobhadfnmpkglie", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day16.getResult(Part.TWO, 16, Day16.getInput(0));
		toConsole(result);
		assertEquals("pclhmengojfdkaib", result);
	}
}
