package buri.aoc.y17.d13;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day13Test {

	@Test
	public void testGetInput() {
		List<Layer> input = Day13.getInput(0);
		assertEquals(43, input.size());
	}

	/**
	 * In the example above, the trip severity is 0*3 + 6*4 = 24.
	 */
	@Test
	public void testPart1Examples() {
		assertEquals(24, Day13.getResult(Part.ONE, Day13.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day13.getResult(Part.ONE, Day13.getInput(0));
		System.out.println("Day 13 Part 1\n\t" + result);
		assertEquals(2688, result);
	}

	/**
	 * Because all smaller delays would get you caught, the fewest number of picoseconds you would need to delay to get
	 * through safely is 10.
	 */
	@Test
	public void testPart2Examples() {
		assertEquals(10, Day13.getResult(Part.TWO, Day13.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day13.getResult(Part.TWO, Day13.getInput(0));
		System.out.println("Day 13 Part 2\n\t" + result);
		assertEquals(0, result);
	}
}
