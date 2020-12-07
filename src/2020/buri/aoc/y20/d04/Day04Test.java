package buri.aoc.y20.d04;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day04Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(290, Day04.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day04.getResult(Part.ONE, Day04.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, Day04.getInput(0));
		toConsole(result);
		assertEquals(237, result);
	}

	@Test
	public void testPart2Examples() {
//		assertEquals(0, Day04.getResult(Part.TWO, Day04.getInput(2)));
		assertEquals(4, Day04.getResult(Part.TWO, Day04.getInput(3)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, Day04.getInput(0));
		toConsole(result);
		assertEquals(172, result);
	}
}
