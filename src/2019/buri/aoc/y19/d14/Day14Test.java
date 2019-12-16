package buri.aoc.y19.d14;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day14Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Reaction> input = Day14.getInput(0);
		assertEquals(59, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(31, Day14.getResult(Part.ONE, Day14.getInput(1)));
		assertEquals(165, Day14.getResult(Part.ONE, Day14.getInput(2)));
		assertEquals(13312, Day14.getResult(Part.ONE, Day14.getInput(3)));
		assertEquals(180697, Day14.getResult(Part.ONE, Day14.getInput(4)));
		assertEquals(2210736, Day14.getResult(Part.ONE, Day14.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day14.getResult(Part.ONE, Day14.getInput(0));
		toClipboard(result);
		System.out.println("Day 14 Part 1\n\t" + result);
		assertEquals(399063, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(82892753, Day14.getResult(Part.TWO, Day14.getInput(3)));
		assertEquals(5586022, Day14.getResult(Part.TWO, Day14.getInput(4)));
		assertEquals(460664, Day14.getResult(Part.TWO, Day14.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day14.getResult(Part.TWO, Day14.getInput(0));
		toClipboard(result);
		System.out.println("Day 14 Part 2\n\t" + result);
		assertEquals(4215654, result);
	}
}
