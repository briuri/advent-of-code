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

	@Test
	public void testPart1Example1() {
		assertEquals(3, Day01.getResult(Part.ONE, "1122"));
	}

	@Test
	public void testPart1Example2() {
		assertEquals(4, Day01.getResult(Part.ONE, "1111"));
	}

	@Test
	public void testPart1Example3() {
		assertEquals(0, Day01.getResult(Part.ONE, "1234"));
	}

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

	@Test
	public void testPart2Example1() {
		assertEquals(6, Day01.getResult(Part.TWO, "1212"));
	}

	@Test
	public void testPart2Example2() {
		assertEquals(0, Day01.getResult(Part.TWO, "1221"));
	}

	@Test
	public void testPart2Example3() {
		assertEquals(4, Day01.getResult(Part.TWO, "123425"));
	}

	@Test
	public void testPart2Example4() {
		assertEquals(12, Day01.getResult(Part.TWO, "123123"));
	}

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
