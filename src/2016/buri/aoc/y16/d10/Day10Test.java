package buri.aoc.y16.d10;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day10.getInput(0);
		assertEquals(231, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day10.getResult(Part.ONE, Day10.getInput(0));
		toConsole(result);
		assertEquals(113, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day10.getResult(Part.TWO, Day10.getInput(0));
		toConsole(result);
		// Not zero.
		assertEquals(12803, result);
	}
}
