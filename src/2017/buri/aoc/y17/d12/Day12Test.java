package buri.aoc.y17.d12;

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
		List<Report> input = Day12.getInput(0);
		assertEquals(2000, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(6, Day12.getResult(Part.ONE, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day12.getResult(Part.ONE, Day12.getInput(0));
		toConsole(result);
		assertEquals(134, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day12.getResult(Part.TWO, Day12.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day12.getResult(Part.TWO, Day12.getInput(0));
		toConsole(result);
		assertEquals(193, result);
	}
}
