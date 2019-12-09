package buri.aoc.template;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day00Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day00.getInput(0);
		assertEquals(1, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(0, Day00.getResult(Part.ONE, Day00.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day00.getResult(Part.ONE, Day00.getInput(0));
		toClipboard(result);
		System.out.println("Day 0 Part 1\n\t" + result);
		assertEquals(0, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(0, Day00.getResult(Part.TWO, Day00.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day00.getResult(Part.TWO, Day00.getInput(0));
		toClipboard(result);
		System.out.println("Day 0 Part 2\n\t" + result);
		assertEquals(0, result);
	}
}
