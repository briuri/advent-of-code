package buri.aoc.y19.d01;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day01Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(100, Day01.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day01.getFuel(Part.ONE, 12));
		assertEquals(2, Day01.getFuel(Part.ONE, 14));
		assertEquals(654, Day01.getFuel(Part.ONE, 1969));
		assertEquals(33583, Day01.getFuel(Part.ONE, 100756));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		toConsole(result);
		assertEquals(3087896, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day01.getFuel(Part.TWO, 14));
		assertEquals(966, Day01.getFuel(Part.TWO, 1969));
		assertEquals(50346, Day01.getFuel(Part.TWO, 100756));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		toConsole(result);
		assertEquals(4628989, result);
	}
}
