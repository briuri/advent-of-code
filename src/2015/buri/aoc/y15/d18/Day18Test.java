package buri.aoc.y15.d18;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day18Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day18.getInput(0);
		assertEquals(100, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Day18.getResult(Part.ONE, 4, Day18.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day18.getResult(Part.ONE, 100, Day18.getInput(0));
		toConsole(result);
		assertEquals(821, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(17, Day18.getResult(Part.TWO, 5, Day18.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day18.getResult(Part.TWO, 100, Day18.getInput(0));
		toConsole(result);
		assertEquals(886, result);
	}
}
