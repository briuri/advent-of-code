package buri.aoc.y2017;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day03Part2Test {

	
	/**
	 * First value over 700 should be 747.
	 */
	@Test
	public void testGenerateGridExample() {
		assertEquals(747, Day03Part2.generateGrid(700));
	}
	
	/**
	 * Solves the Day 3 Part 2 puzzle against the real input.
	 */
	@Test
	public void testPart2RealInput() {
		System.out.println("Day 3 Part 2 limitValue=" + Day03Part2.generateGrid(312051));
	}
}
