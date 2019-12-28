package buri.aoc.y17.d20;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day20Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(1000, Day20.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(0, Day20.getResult(Part.ONE, Day20.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day20.getResult(Part.ONE, Day20.getInput(0));
		toConsole(result);
		assertEquals(364, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1, Day20.getResult(Part.TWO, Day20.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day20.getResult(Part.TWO, Day20.getInput(0));
		toConsole(result);
		assertEquals(420, result);
	}
}
