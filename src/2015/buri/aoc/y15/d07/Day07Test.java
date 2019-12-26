package buri.aoc.y15.d07;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day07Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day07.getInput(0);
		assertEquals(339, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertNull(Day07.getResult(Part.ONE, Day07.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day07.getResult(Part.ONE, Day07.getInput(0));
		toConsole(result);
		assertEquals("46065", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day07.getResult(Part.TWO, Day07.getInput(0));
		toConsole(result);
		assertEquals("14134", result);
	}
}
