package buri.aoc.y2017;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day03Part1Test {

	@Test(expected = IllegalArgumentException.class)
	public void testGetManhattanDistanceValueTooLow() {
		Day03Part1.getManhattanDistance(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetManhattanDistanceValueTooHigh() {
		Day03Part1.getManhattanDistance(Day03Part1.MAX_VALUE + 1);
	}
	
	/**
	 * Data from square 1 is carried 0 steps, since it's at the access port.
	 */
	@Test
	public void testPart1Example1() {
		assertEquals(0, Day03Part1.getManhattanDistance(1));
	}

	/**
	 * Data from square 12 is carried 3 steps, such as: down, left, left.
	 */
	@Test
	public void testPart1Example2() {
		assertEquals(3, Day03Part1.getManhattanDistance(12));
	}

	/**
	 * Data from square 23 is carried only 2 steps: up twice.
	 */
	@Test
	public void testPart1Example3() {
		assertEquals(2, Day03Part1.getManhattanDistance(23));
	}

	/**
	 * Data from square 1024 must be carried 31 steps.
	 */
	@Test
	public void testPart1Example4() {
		assertEquals(31, Day03Part1.getManhattanDistance(1024));
	}

	/**
	 * Solves the Day 3 Part 1 puzzle against the real input.
	 */
	@Test
	public void testPart1RealInput() {
		System.out.println("Day 3 Part 1 md=" + Day03Part1.getManhattanDistance(312051));
	}
}
