package buri.aoc.y19.d11;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day11Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day11.getInput(0);
		assertEquals(613, input.size());
	}
	
	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day11.getResult(Part.ONE, Day11.getInput(0));
		toClipboard(result);
		System.out.println("Day 11 Part 1\n\t" + result);
		assertEquals(2319, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day11.getResult(Part.TWO, Day11.getInput(0));
		toClipboard(result);
		System.out.println("Day 11 Part 2\n\t" + result);
		//UERPRFGJ
		assertEquals(0, result);
	}
}
