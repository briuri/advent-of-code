package buri.aoc.y18.d17;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day17Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(2024, Day17.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(57, Day17.getResult(Part.ONE, Day17.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day17.getResult(Part.ONE, Day17.getInput(0));
		toConsole(result);
		assertEquals(52800, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(29, Day17.getResult(Part.TWO, Day17.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day17.getResult(Part.TWO, Day17.getInput(0));
		toConsole(result);
		assertEquals(45210, result);
	}
}
