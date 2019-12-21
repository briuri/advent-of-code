package buri.aoc.y19.d19;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day19Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day19.getInput(0);
		assertEquals(424, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day19.getResult(Part.ONE, Day19.getInput(0));
		toClipboard(result);
		System.out.println("Day 19 Part 1\n\t" + result);
		assertEquals(203, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day19.getResult(Part.TWO, Day19.getInput(0));
		toClipboard(result);
		System.out.println("Day 19 Part 2\n\t" + result);
		assertEquals(8771057, result);
	}
}
