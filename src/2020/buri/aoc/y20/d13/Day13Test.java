package buri.aoc.y20.d13;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day13Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(2, Day13.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(295, Day13.getResult(Part.ONE, Day13.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day13.getResult(Part.ONE, Day13.getInput(0));
		toConsole(result);
		assertEquals(2845L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1068781L, Day13.getResult(Part.TWO, Day13.getInput(1)));
		assertEquals(754018L, Day13.getResult(Part.TWO, Day13.getInput(2)));
		assertEquals(779210L, Day13.getResult(Part.TWO, Day13.getInput(3)));
		assertEquals(1261476L, Day13.getResult(Part.TWO, Day13.getInput(4)));
		assertEquals(1202161486L, Day13.getResult(Part.TWO, Day13.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day13.getResult(Part.TWO, Day13.getInput(0));
		toConsole(result);
		assertEquals(487905974205117L, result);
	}
}
