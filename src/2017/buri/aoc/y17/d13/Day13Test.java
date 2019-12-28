package buri.aoc.y17.d13;

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
		assertEquals(43, Day13.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(24, Day13.getResult(Part.ONE, Day13.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day13.getResult(Part.ONE, Day13.getInput(0));
		toConsole(result);
		assertEquals(2688, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(10, Day13.getResult(Part.TWO, Day13.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day13.getResult(Part.TWO, Day13.getInput(0));
		toConsole(result);
		assertEquals(3876272, result);
	}
}
