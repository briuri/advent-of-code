package buri.aoc.y16.d23;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day23Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(26, Day23.getInput(0).size());
	}

	@Test
	public void testInputConversion() {
		Registers.convertInput(Day23.getInput(0));
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3L, Day23.getResult(Part.ONE, Day23.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day23.getResult(Part.ONE, Day23.getInput(0));
		toConsole(result);
		assertEquals(12315L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day23.getResult(Part.TWO, Day23.getInput(0));
		toConsole(result);
		assertEquals(479008875L, result);
	}
}
