package buri.aoc.y20.d10;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(96, Day10.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(35, Day10.getResult(Part.ONE, Day10.getInput(1)));
		assertEquals(220, Day10.getResult(Part.ONE, Day10.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day10.getResult(Part.ONE, Day10.getInput(0));
		toConsole(result);
		assertEquals(1890L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8, Day10.getResult(Part.TWO, Day10.getInput(1)));
		assertEquals(19208, Day10.getResult(Part.TWO, Day10.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day10.getResult(Part.TWO, Day10.getInput(0));
		toConsole(result);
		assertEquals(49607173328384L, result);
	}
}
