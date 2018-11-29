package buri.aoc.y17.d06;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day06Test {
	
	@Test
	public void testGetInput() {
		List<Integer> banks = Day06.getInput();
		assertEquals(16, banks.size());
		assertEquals(Integer.valueOf(4), banks.get(0));
	}
	
	/**
	 * For example, imagine a scenario with only four memory banks:
	 * 
	 * 0 2 7 0
	 * 
	 * The infinite loop is detected after the fifth block redistribution cycle, and so the answer in this example is 5.
	 */
	@Test
	public void testPart1Example1() {
		List<Integer> banks = new ArrayList<>();
		banks.add(Integer.valueOf(0));
		banks.add(Integer.valueOf(2));
		banks.add(Integer.valueOf(7));
		banks.add(Integer.valueOf(0));
		assertEquals(5, Day06.getIterations(Part.ONE, banks));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day06.getIterations(Part.ONE, Day06.getInput());
		System.out.println("Day 6 Part 1\n\t" + result);
		assertEquals(12841, result);
	}
	
	/**
	 * In the example above, 2 4 1 2 is seen again after four cycles, and so the answer in that example would be 4.
	 */
	@Test
	public void testPart2Example1() {
		List<Integer> banks = new ArrayList<>();
		banks.add(Integer.valueOf(0));
		banks.add(Integer.valueOf(2));
		banks.add(Integer.valueOf(7));
		banks.add(Integer.valueOf(0));
		assertEquals(4, Day06.getIterations(Part.TWO, banks));
	}
	
	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day06.getIterations(Part.TWO, Day06.getInput());
		System.out.println("Day 6 Part 2\n\t" + result);
		assertEquals(8038, result);
	}
}
