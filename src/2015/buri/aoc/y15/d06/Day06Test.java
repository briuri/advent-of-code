package buri.aoc.y15.d06;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day06Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day06.getInput(0);
		assertEquals(300, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day06.getResult(Part.ONE, Day06.getInput(0));
		toConsole(result);
		assertEquals(400410, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day06.getResult(Part.TWO, Day06.getInput(0));
		toConsole(result);
		assertEquals(15343601, result);
	}
}
