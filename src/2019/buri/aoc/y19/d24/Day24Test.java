package buri.aoc.y19.d24;

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
		assertEquals(5, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2129920, Day24.getResult(Part.ONE, Day24.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day24.getResult(Part.ONE, Day24.getInput(0));
		toClipboard(result);
		System.out.println("Day 24 Part 1\n\t" + result);
		assertEquals(18842609, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day24.getResult(Part.TWO, Day24.getInput(0));
		toClipboard(result);
		System.out.println("Day 24 Part 2\n\t" + result);
		assertEquals(2059, result);
	}
}
