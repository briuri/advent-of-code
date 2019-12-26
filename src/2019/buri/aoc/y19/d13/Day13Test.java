package buri.aoc.y19.d13;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day13Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day13.getInput(0);
		assertEquals(2416, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day13.getResult(Part.ONE, Day13.getInput(0));
		toConsole(result);
		assertEquals(286, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day13.getResult(Part.TWO, Day13.getInput(0));
		toConsole(result);
		assertEquals(14538, result);
	}
}
