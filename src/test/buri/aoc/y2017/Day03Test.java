package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day03Test {

	@Test
	public void testGetXForValue() {
		assertEquals(1, Day03.getXForValue(2));
		assertEquals(1, Day03.getXForValue(3));
		assertEquals(1, Day03.getXForValue(4));
		assertEquals(1, Day03.getXForValue(5));
		assertEquals(1, Day03.getXForValue(6));
		assertEquals(1, Day03.getXForValue(7));
		assertEquals(1, Day03.getXForValue(8));
		assertEquals(1, Day03.getXForValue(9));
		assertEquals(3, Day03.getXForValue(10));
		assertEquals(3, Day03.getXForValue(11));
		assertEquals(3, Day03.getXForValue(12));
		assertEquals(3, Day03.getXForValue(13));
		assertEquals(3, Day03.getXForValue(14));
		assertEquals(3, Day03.getXForValue(15));
		assertEquals(3, Day03.getXForValue(16));
		assertEquals(3, Day03.getXForValue(17));
		assertEquals(3, Day03.getXForValue(18));
		assertEquals(3, Day03.getXForValue(19));
		assertEquals(3, Day03.getXForValue(20));
		assertEquals(3, Day03.getXForValue(21));
		assertEquals(3, Day03.getXForValue(22));
		assertEquals(3, Day03.getXForValue(23));
		assertEquals(3, Day03.getXForValue(24));
		assertEquals(3, Day03.getXForValue(25));
		assertEquals(5, Day03.getXForValue(26));
		assertEquals(631, Day03.getXForValue(Day03.MAX_VALUE));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetXForValueTooLow() {
		Day03.getXForValue(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetXForValueTooHigh() {
		Day03.getXForValue(Day03.MAX_VALUE + 1);
	}
	
	/**
	 * Data from square 1 is carried 0 steps, since it's at the access port.
	 */
	@Test
	public void testPart1Example1() throws IOException {
		assertEquals(0, Day03.getManhattanDistance(1));
	}

	/**
	 * Data from square 12 is carried 3 steps, such as: down, left, left.
	 */
	@Test
	public void testPart1Example2() throws IOException {
		assertEquals(3, Day03.getManhattanDistance(12));
	}
    
	/**
	 * Data from square 23 is carried only 2 steps: up twice.
	 */
	@Test
	public void testPart1Example3() throws IOException {
		assertEquals(2, Day03.getManhattanDistance(23));
	}
	
	/**
	 * Data from square 1024 must be carried 31 steps.
	 */
	@Test
	public void testPart1Example4() throws IOException {
		assertEquals(31, Day03.getManhattanDistance(1024));
	}
	
	/**
	 * Solves the Day 3 Part 1 puzzle against the real input.
	 */
	@Test
	public void testPart1RealInput() throws IOException {
		System.out.println("Day 3 Part 1 md=" + Day03.getManhattanDistance(312051));
	}
}
