package buri.aoc.y20.d14;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day14Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(559, Day14.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(165, Day14.getResult(Part.ONE, Day14.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day14.getResult(Part.ONE, Day14.getInput(0));
		toConsole(result);
		assertEquals(7440382076205L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(208, Day14.getResult(Part.TWO, Day14.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day14.getResult(Part.TWO, Day14.getInput(0));
		toConsole(result);
		assertEquals(4200656704538L, result);
	}
}
