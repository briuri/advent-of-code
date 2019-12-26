package buri.aoc.y19.d17;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day17Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day17.getInput(0);
		assertEquals(1485, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day17.getResult(Part.ONE, Day17.getInput(0));
		toConsole(result);
		assertEquals(3660, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day17.getResult(Part.TWO, Day17.getInput(0));
		toConsole(result);
		assertEquals(962913, result);
	}
}
