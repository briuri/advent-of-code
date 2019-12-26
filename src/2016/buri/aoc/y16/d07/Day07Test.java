package buri.aoc.y16.d07;

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
		assertEquals(2000, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day07.getResult(Part.ONE, Day07.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day07.getResult(Part.ONE, Day07.getInput(0));
		toConsole(result);
		assertEquals(110, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Day07.getResult(Part.TWO, Day07.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day07.getResult(Part.TWO, Day07.getInput(0));
		toConsole(result);
		assertEquals(242, result);
	}
}
