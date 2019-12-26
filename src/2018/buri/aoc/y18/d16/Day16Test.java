package buri.aoc.y18.d16;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test extends BaseTest {

	@Test
	public void testGetInput() {
		// Input split into Part 1 and Part 2 files.
		List<String> input = Day16.getInput(0);
		assertEquals(3248, input.size());
		input = Day16.getInput(2);
		assertEquals(902, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(1, Day16.getResult(Part.ONE, Day16.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day16.getResult(Part.ONE, Day16.getInput(0));
		toConsole(result);
		assertEquals(592, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day16.getResult(Part.TWO, Day16.getInput(2));
		toConsole(result);
		assertEquals(557, result);
	}
}
