package buri.aoc.y20.d05;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day05Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(743, Day05.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(820, Day05.getResult(Part.ONE, Day05.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day05.getResult(Part.ONE, Day05.getInput(0));
		toConsole(result);
		assertEquals(838, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		// 715
		int result = Day05.getResult(Part.TWO, Day05.getInput(0));
		toConsole(result);
		assertEquals(714, result);
	}
}
