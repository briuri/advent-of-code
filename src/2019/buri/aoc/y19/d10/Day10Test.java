package buri.aoc.y19.d10;

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
		assertEquals(23, Day10.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(8, Day10.getResult(Part.ONE, Day10.getInput(1)));
		assertEquals(33, Day10.getResult(Part.ONE, Day10.getInput(2)));
		assertEquals(35, Day10.getResult(Part.ONE, Day10.getInput(3)));
		assertEquals(41, Day10.getResult(Part.ONE, Day10.getInput(4)));
		assertEquals(210, Day10.getResult(Part.ONE, Day10.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day10.getResult(Part.ONE, Day10.getInput(0));
		toConsole(result);
		assertEquals(230, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(802, Day10.getResult(Part.TWO, Day10.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day10.getResult(Part.TWO, Day10.getInput(0));
		toConsole(result);
		assertEquals(1205, result);
	}
}
