package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.util.List;
import java.io.IOException;

import org.junit.Test;

import buri.aoc.util.FileUtil;
import buri.aoc.y2017.Day01.Strategy;

/**
 * @author Brian Uri!
 */
public class Day01Test {

	@Test(expected = IOException.class)
	public void testConstructorInvalidInput() throws IOException {
		new Day01("NaN", Strategy.MATCH_NEXT);
	}

	@Test(expected = IOException.class)
	public void testConstructorNullInput() throws IOException {
		new Day01(null, Strategy.MATCH_NEXT);
	}

	@Test
	public void testConstructorValidInput() throws IOException {
		Day01 runner = new Day01("1", Strategy.MATCH_NEXT);
		assertEquals("1", runner.getInput());
	}

	@Test(expected = IOException.class)
	public void testConstructorUnevenInput() throws IOException {
		new Day01("123", Strategy.MATCH_HALFWAY);
	}

	@Test
	public void testConstructorEvenInput() throws IOException {
		Day01 runner = new Day01("12", Strategy.MATCH_NEXT);
		assertEquals("12", runner.getInput());
	}

	/**
	 * 1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the
	 * second digit and the third digit (2) matches the fourth digit.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPart1Example1() throws IOException {
		Day01 runner = new Day01("1122", Strategy.MATCH_NEXT);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(2, matchingDigits.size());
		assertEquals(Integer.valueOf(1), matchingDigits.get(0));
		assertEquals(Integer.valueOf(2), matchingDigits.get(1));
		assertEquals(3, runner.getSum());
	}

	/**
	 * 1111 produces 4 because each digit (all 1) matches the next.
	 */
	@Test
	public void testPart1Example2() throws IOException {
		Day01 runner = new Day01("1111", Strategy.MATCH_NEXT);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(4, matchingDigits.size());
		for (int i = 0; i < matchingDigits.size(); i++) {
			assertEquals(Integer.valueOf(1), matchingDigits.get(i));
		}
		assertEquals(4, runner.getSum());
	}

	/**
	 * 1234 produces 0 because no digit matches the next.
	 */
	@Test
	public void testPart1Example3() throws IOException {
		Day01 runner = new Day01("1234", Strategy.MATCH_NEXT);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(0, matchingDigits.size());
		assertEquals(0, runner.getSum());
	}

	/**
	 * 91212129 produces 9 because the only digit that matches the next one is
	 * the last digit, 9.
	 */
	@Test
	public void testPart1Example4() throws IOException {
		Day01 runner = new Day01("91212129", Strategy.MATCH_NEXT);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(1, matchingDigits.size());
		assertEquals(Integer.valueOf(9), matchingDigits.get(0));
		assertEquals(9, runner.getSum());
	}

	/**
	 * Solves the Day 1 Part 1 puzzle against the real input file.
	 */
	@Test
	public void testPart1RealInput() throws IOException {
		String input = FileUtil.getDay1NumberString("data/2017-01.txt");
		Day01 runner = new Day01(input, Strategy.MATCH_NEXT);
		System.out.println("Day 1 Part 1 sum=" + runner.getSum());
	}

	/**
	 * 1212 produces 6: the list contains 4 items, and all four digits match the
	 * digit 2 items ahead.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPart2Example1() throws IOException {
		Day01 runner = new Day01("1212", Strategy.MATCH_HALFWAY);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(4, matchingDigits.size());
		assertEquals(Integer.valueOf(1), matchingDigits.get(0));
		assertEquals(Integer.valueOf(2), matchingDigits.get(1));
		assertEquals(Integer.valueOf(1), matchingDigits.get(2));
		assertEquals(Integer.valueOf(2), matchingDigits.get(3));
		assertEquals(6, runner.getSum());
	}

	/**
	 * 1221 produces 0, because every comparison is between a 1 and a 2.
	 */
	@Test
	public void testPart2Example2() throws IOException {
		Day01 runner = new Day01("1221", Strategy.MATCH_HALFWAY);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(0, matchingDigits.size());
		assertEquals(0, runner.getSum());
	}

	/**
	 * 123425 produces 4, because both 2s match each other, but no other digit
	 * has a match.
	 */
	@Test
	public void testPart2Example3() throws IOException {
		Day01 runner = new Day01("123425", Strategy.MATCH_HALFWAY);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(2, matchingDigits.size());
		for (int i = 0; i < matchingDigits.size(); i++) {
			assertEquals(Integer.valueOf(2), matchingDigits.get(i));
		}
		assertEquals(4, runner.getSum());
	}

	/**
	 * 123123 produces 12.
	 */
	@Test
	public void testPart2Example4() throws IOException {
		Day01 runner = new Day01("123123", Strategy.MATCH_HALFWAY);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(6, matchingDigits.size());
		assertEquals(Integer.valueOf(1), matchingDigits.get(0));
		assertEquals(Integer.valueOf(2), matchingDigits.get(1));
		assertEquals(Integer.valueOf(3), matchingDigits.get(2));
		assertEquals(Integer.valueOf(1), matchingDigits.get(3));
		assertEquals(Integer.valueOf(2), matchingDigits.get(4));
		assertEquals(Integer.valueOf(3), matchingDigits.get(5));
		assertEquals(12, runner.getSum());
	}

	/**
	 * 12131415 produces 4.
	 */
	@Test
	public void testPart2Example5() throws IOException {
		Day01 runner = new Day01("12131415", Strategy.MATCH_HALFWAY);
		List<Integer> matchingDigits = runner.getMatchingDigits();
		assertEquals(4, matchingDigits.size());
		for (int i = 0; i < matchingDigits.size(); i++) {
			assertEquals(Integer.valueOf(1), matchingDigits.get(i));
		}
		assertEquals(4, runner.getSum());
	}

	/**
	 * Solves the Day 1 Part 1 puzzle against the real input file.
	 */
	@Test
	public void testPart2RealInput() throws IOException {
		String input = FileUtil.getDay1NumberString("data/2017-01.txt");
		Day01 runner = new Day01(input, Strategy.MATCH_HALFWAY);
		System.out.println("Day 1 Part 2 sum=" + runner.getSum());
	}
}
