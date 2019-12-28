package buri.aoc.y18.d02;

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
		assertEquals(250, Day02.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("12", Day02.getResult(Part.ONE, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day02.getResult(Part.ONE, Day02.getInput(0));
		toConsole(result);
		assertEquals("5750", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("fgij", Day02.getResult(Part.TWO, Day02.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day02.getResult(Part.TWO, Day02.getInput(0));
		toConsole(result);
		assertEquals("tzyvunogzariwkpcbdewmjhxi", result);
	}
}
