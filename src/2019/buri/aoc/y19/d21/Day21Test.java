package buri.aoc.y19.d21;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day21Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day21.getInput(0);
		assertEquals(2050, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day21.getResult(Part.ONE, Day21.getInput(0));
		toConsole(result);
		assertEquals(19360288, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day21.getResult(Part.TWO, Day21.getInput(0));
		toConsole(result);
		assertEquals(1143814750, result);
	}
}
