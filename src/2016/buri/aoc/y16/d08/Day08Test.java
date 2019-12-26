package buri.aoc.y16.d08;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day08Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day08.getInput(0);
		assertEquals(162, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day08.getResult(Part.ONE, Day08.getInput(0));
		toConsole(result);
		assertEquals(116, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		// Visual inspection: UPOJFLBCEZ
		int result = Day08.getResult(Part.TWO, Day08.getInput(0));
		toConsole(result);
		assertEquals(116, result);
	}
}
