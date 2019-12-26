package buri.aoc.y15.d24;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day24Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day24.getInput(0);
		assertEquals(29, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(99, Day24.getResult(Part.ONE, Day24.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day24.getResult(Part.ONE, Day24.getInput(0));
		toConsole(result);
		assertEquals(10723906903L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(44, Day24.getResult(Part.TWO, Day24.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day24.getResult(Part.TWO, Day24.getInput(0));
		toConsole(result);
		assertEquals(74850409, result);
	}
}
