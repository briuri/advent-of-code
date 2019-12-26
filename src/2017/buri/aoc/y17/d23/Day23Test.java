package buri.aoc.y17.d23;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day23Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day23.getInput(0);
		assertEquals(32, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day23.getResult(Part.ONE, Day23.getInput(0));
		toConsole(result);
		assertEquals(3025, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day23.getResult(Part.TWO, Day23.getInput(0));
		toConsole(result);
		assertEquals(915, result);
	}
}
