package buri.aoc.y19.d06;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day06Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day06.getInput(0);
		assertEquals(1805, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(42, Day06.getResult(Part.ONE, Day06.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day06.getResult(Part.ONE, Day06.getInput(0));
		toClipboard(result);
		System.out.println("Day 6 Part 1\n\t" + result);
		assertEquals(314702, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4, Day06.getResult(Part.TWO, Day06.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day06.getResult(Part.TWO, Day06.getInput(0));
		toClipboard(result);
		System.out.println("Day 6 Part 2\n\t" + result);
		assertEquals(439, result);
	}
}