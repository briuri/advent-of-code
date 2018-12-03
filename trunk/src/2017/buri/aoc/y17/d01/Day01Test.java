package buri.aoc.y17.d01;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day01Test {

	@Test
	public void testGetInput() {
		String input = Day01.getInput(0);
		assertEquals(2074, input.length());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetResultInvalidInput() {
		Day01.getResult(Part.ONE, "NaN");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetResultNullInput() {
		Day01.getResult(Part.ONE, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetResultUnevenInput() {
		Day01.getResult(Part.TWO, "123");
	}

	/**
	 * 1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the
	 * second digit and the third digit (2) matches the fourth digit.
	 */
	@Test
	public void testPart1Example1() {
		assertEquals(3, Day01.getResult(Part.ONE, "1122"));
	}

	/**
	 * 1111 produces 4 because each digit (all 1) matches the next.
	 */
	@Test
	public void testPart1Example2() {
		assertEquals(4, Day01.getResult(Part.ONE, "1111"));
	}

	/**
	 * 1234 produces 0 because no digit matches the next.
	 */
	@Test
	public void testPart1Example3() {
		assertEquals(0, Day01.getResult(Part.ONE, "1234"));
	}

	/**
	 * 91212129 produces 9 because the only digit that matches the next one is
	 * the last digit, 9.
	 */
	@Test
	public void testPart1Example4() {
		assertEquals(9, Day01.getResult(Part.ONE, "91212129"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		System.out.println("Day 1 Part 1\n\t" + result);
		assertEquals(1171, result);
	}

	/**
	 * 1212 produces 6: the list contains 4 items, and all four digits match the
	 * digit 2 items ahead.
	 */
	@Test
	public void testPart2Example1() {
		assertEquals(6, Day01.getResult(Part.TWO, "1212"));
	}

	/**
	 * 1221 produces 0, because every comparison is between a 1 and a 2.
	 */
	@Test
	public void testPart2Example2() {
		assertEquals(0, Day01.getResult(Part.TWO, "1221"));
	}

	/**
	 * 123425 produces 4, because both 2s match each other, but no other digit
	 * has a match.
	 */
	@Test
	public void testPart2Example3() {
		assertEquals(4, Day01.getResult(Part.TWO, "123425"));
	}

	/**
	 * 123123 produces 12.
	 */
	@Test
	public void testPart2Example4() {
		assertEquals(12, Day01.getResult(Part.TWO, "123123"));
	}

	/**
	 * 12131415 produces 4.
	 */
	@Test
	public void testPart2Example5() {
		assertEquals(4, Day01.getResult(Part.TWO, "12131415"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		System.out.println("Day 1 Part 2\n\t" + result);
		assertEquals(1024, result);
	}
}
