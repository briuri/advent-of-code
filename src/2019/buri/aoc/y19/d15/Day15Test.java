package buri.aoc.y19.d15;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day15Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day15.getInput(0);
		assertEquals(1045, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day15.getResult(Part.ONE, Day15.getInput(0));
		toClipboard(result);
		System.out.println("Day 15 Part 1\n\t" + result);
		assertEquals(280, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day15.getResult(Part.TWO, Day15.getInput(0));
		toClipboard(result);
		System.out.println("Day 15 Part 2\n\t" + result);
		assertEquals(400, result);
	}
}
