package buri.aoc.y15.d09;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day09Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day09.getInput(0);
		assertEquals(28, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(605, Day09.getResult(Part.ONE, Day09.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day09.getResult(Part.ONE, Day09.getInput(0));
		toConsole(result);
		assertEquals(141, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(982, Day09.getResult(Part.TWO, Day09.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day09.getResult(Part.TWO, Day09.getInput(0));
		toConsole(result);
		assertEquals(736, result);
	}
}
