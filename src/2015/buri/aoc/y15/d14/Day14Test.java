package buri.aoc.y15.d14;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day14Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day14.getInput(0);
		assertEquals(9, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day14.getResult(Part.ONE, Day14.getInput(0));
		toConsole(result);
		assertEquals(2640, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day14.getResult(Part.TWO, Day14.getInput(0));
		toConsole(result);
		assertEquals(1102, result);
	}
}
