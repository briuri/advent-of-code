package buri.aoc.y16.d12;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day12Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day12.getInput(0);
		assertEquals(23, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(42, Day12.getResult(Part.ONE, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day12.getResult(Part.ONE, Day12.getInput(0));
		toConsole(result);
		assertEquals(318007, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day12.getResult(Part.TWO, Day12.getInput(0));
		toConsole(result);
		assertEquals(9227661, result);
	}
}
