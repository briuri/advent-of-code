package buri.aoc.y18.d19;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;
import buri.aoc.data.registers.IndexedRegisters;

/**
 * @author Brian Uri!
 */
public class Day19Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(37, Day19.getInput(0).size());
	}

	@Test
	public void testInputConversion() {
		IndexedRegisters.convertInput(Day19.getInput(0));
	}

	@Test
	public void testPart1Examples() {
		assertEquals(6, Day19.getResult(Part.ONE, Day19.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day19.getResult(Part.ONE, Day19.getInput(0));
		toConsole(result);
		assertEquals(1056, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day19.getResult(Part.TWO, Day19.getInput(0));
		toConsole(result);
		assertEquals(10915260, result);
	}
}
