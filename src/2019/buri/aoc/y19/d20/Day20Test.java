package buri.aoc.y19.d20;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day20Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day20.getInput(0);
		assertEquals(134, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(23, Day20.getResult(Part.ONE, Day20.getInput(1)));
		assertEquals(58, Day20.getResult(Part.ONE, Day20.getInput(2)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day20.getResult(Part.ONE, Day20.getInput(0));
		toClipboard(result);
		System.out.println("Day 20 Part 1\n\t" + result);
		assertEquals(684, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(26, Day20.getResult(Part.TWO, Day20.getInput(1)));
		assertEquals(396, Day20.getResult(Part.TWO, Day20.getInput(3)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day20.getResult(Part.TWO, Day20.getInput(0));
		toClipboard(result);
		System.out.println("Day 20 Part 2\n\t" + result);
		assertEquals(7758, result);
	}
}
