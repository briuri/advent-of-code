package buri.aoc.y18.d08;

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
		List<Integer> input = Day08.getInput(0);
		assertEquals(18780, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(138, Day08.getResult(Part.ONE, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day08.getResult(Part.ONE, Day08.getInput(0));
		toConsole(result);
		assertEquals(48260, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(66, Day08.getResult(Part.TWO, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day08.getResult(Part.TWO, Day08.getInput(0));
		toConsole(result);
		assertEquals(25981, result);
	}
}
