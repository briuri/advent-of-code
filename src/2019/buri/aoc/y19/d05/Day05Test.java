package buri.aoc.y19.d05;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day05Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day05.getInput(0);
		assertEquals(678, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day05.getResult(Part.ONE, Day05.getInput(0));
		toConsole(result);
		assertEquals(15259545L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(999L, Day05.getResult(Part.ONE, Day05.getInput(1)));
	}
	
	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day05.getResult(Part.TWO, Day05.getInput(0));
		toConsole(result);
		assertEquals(7616021L, result);
	}
}
