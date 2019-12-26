package buri.aoc.y17.d19;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day19Test extends BaseTest {

	@Test
	public void testGetInput() {
		Diagram input = Day19.getInput(0);
		assertNotNull(input);
	}

	@Test
	public void testPart1Examples() {
		assertEquals("ABCDEF", Day19.getResult(Part.ONE, Day19.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day19.getResult(Part.ONE, Day19.getInput(0));
		toConsole(result);
		assertEquals("LXWCKGRAOY", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("38", Day19.getResult(Part.TWO, Day19.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day19.getResult(Part.TWO, Day19.getInput(0));
		toConsole(result);
		assertEquals("17302", result);
	}
}
