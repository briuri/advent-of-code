package buri.aoc.y19.d09;

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
		List<Long> input = Day09.getInput(0);
		assertEquals(973, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(99, Day09.getResult(Part.ONE, Day09.getInput(1)));
		assertEquals(1219070632396864L, Day09.getResult(Part.ONE, Day09.getInput(2)));
		assertEquals(1125899906842624L, Day09.getResult(Part.ONE, Day09.getInput(3)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day09.getResult(Part.ONE, Day09.getInput(0));
		toConsole(result);
		assertEquals(2870072642L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day09.getResult(Part.TWO, Day09.getInput(0));
		toConsole(result);
		assertEquals(58534L, result);
	}
}
