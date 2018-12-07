package buri.aoc.y18.d01;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day01Test {

	/**
	 * Boilerplate conversion for example input
	 */
	private static List<Integer> getInput(String values) {
		List<String> input = new ArrayList<>(Arrays.asList(values.split(" ")));
		return (Day01.convertStringsToInts(input));
	}

	@Test
	public void testGetInput() {
		List<Integer> input = Day01.getInput(0);
		assertEquals(965, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day01.getResult(Part.ONE, getInput("+1 -2 +3 +1")));
		assertEquals(3, Day01.getResult(Part.ONE, getInput("+1 +1 +1")));
		assertEquals(0, Day01.getResult(Part.ONE, getInput("+1 +1 -2")));
		assertEquals(-6, Day01.getResult(Part.ONE, getInput("-1 -2 -3")));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		System.out.println("Day 1 Part 1\n\t" + result);
		assertEquals(442, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Day01.getResult(Part.TWO, getInput("+1 -2 +3 +1")));
		assertEquals(0, Day01.getResult(Part.TWO, getInput("+1 -1")));
		assertEquals(10, Day01.getResult(Part.TWO, getInput("+3 +3 +4 -2 -4")));
		assertEquals(5, Day01.getResult(Part.TWO, getInput("-6 +3 +8 +5 -6")));
		assertEquals(14, Day01.getResult(Part.TWO, getInput("+7 +7 -2 -7 -4")));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		System.out.println("Day 1 Part 2\n\t" + result);
		assertEquals(59908, result);
	}
}
