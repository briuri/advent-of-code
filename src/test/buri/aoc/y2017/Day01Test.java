package buri.aoc.y2017;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.y2017.Day01.Strategy;

/**
 * @author Brian Uri!
 */
public class Day01Test {

	@Test
	public void getCaptchasFromFile() {
		String content = Day01.getCaptchasFromFile("data/2017/01.txt");
		assertEquals(2074, content.length());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCaptchasFromFileFailure() {
		Day01.getCaptchasFromFile("unknown");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSumInvalidInput() {
		Day01.getSum(Strategy.MATCH_NEXT, "NaN");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSumNullInput() {
		Day01.getSum(Strategy.MATCH_NEXT, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSumUnevenInput() {
		Day01.getSum(Strategy.MATCH_HALFWAY, "123");
	}

	/**
	 * 1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the
	 * second digit and the third digit (2) matches the fourth digit.
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testPart1Example1() {
		assertEquals(3, Day01.getSum(Strategy.MATCH_NEXT, "1122"));
	}

	/**
	 * 1111 produces 4 because each digit (all 1) matches the next.
	 */
	@Test
	public void testPart1Example2() {
		assertEquals(4, Day01.getSum(Strategy.MATCH_NEXT, "1111"));
	}

	/**
	 * 1234 produces 0 because no digit matches the next.
	 */
	@Test
	public void testPart1Example3() {
		assertEquals(0, Day01.getSum(Strategy.MATCH_NEXT, "1234"));
	}

	/**
	 * 91212129 produces 9 because the only digit that matches the next one is
	 * the last digit, 9.
	 */
	@Test
	public void testPart1Example4() {
		assertEquals(9, Day01.getSum(Strategy.MATCH_NEXT, "91212129"));
	}

	/**
	 * Solves the Day 1 Part 1 puzzle against the real input file.
	 */
	@Test
	public void testPart1RealInput() {
		String input = Day01.getCaptchasFromFile("data/2017/01.txt");
		System.out.println("Day 1 Part 1 sum=" + Day01.getSum(Strategy.MATCH_NEXT, input));
	}

	/**
	 * 1212 produces 6: the list contains 4 items, and all four digits match the
	 * digit 2 items ahead.
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testPart2Example1() {
		assertEquals(6, Day01.getSum(Strategy.MATCH_HALFWAY, "1212"));
	}

	/**
	 * 1221 produces 0, because every comparison is between a 1 and a 2.
	 */
	@Test
	public void testPart2Example2() {
		assertEquals(0, Day01.getSum(Strategy.MATCH_HALFWAY, "1221"));
	}

	/**
	 * 123425 produces 4, because both 2s match each other, but no other digit
	 * has a match.
	 */
	@Test
	public void testPart2Example3() {
		assertEquals(4, Day01.getSum(Strategy.MATCH_HALFWAY, "123425"));
	}

	/**
	 * 123123 produces 12.
	 */
	@Test
	public void testPart2Example4() {
		assertEquals(12, Day01.getSum(Strategy.MATCH_HALFWAY, "123123"));
	}

	/**
	 * 12131415 produces 4.
	 */
	@Test
	public void testPart2Example5() {
		assertEquals(4, Day01.getSum(Strategy.MATCH_HALFWAY, "12131415"));
	}

	/**
	 * Solves the Day 1 Part 1 puzzle against the real input file.
	 */
	@Test
	public void testPart2RealInput() {
		String input = Day01.getCaptchasFromFile("data/2017/01.txt");
		System.out.println("Day 1 Part 2 sum=" + Day01.getSum(Strategy.MATCH_HALFWAY, input));
	}
}
