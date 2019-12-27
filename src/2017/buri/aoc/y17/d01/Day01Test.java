package buri.aoc.y17.d01;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day01Test extends BaseTest {

	@Test
	public void testGetInput() {
		String input = Day01.getInput(0);
		assertEquals(2074, input.length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day01.getResult(Part.ONE, "1122"));
		assertEquals(4, Day01.getResult(Part.ONE, "1111"));
		assertEquals(0, Day01.getResult(Part.ONE, "1234"));
		assertEquals(9, Day01.getResult(Part.ONE, "91212129"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day01.getResult(Part.ONE, Day01.getInput(0));
		toConsole(result);
		assertEquals(1171, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(6, Day01.getResult(Part.TWO, "1212"));
		assertEquals(0, Day01.getResult(Part.TWO, "1221"));
		assertEquals(4, Day01.getResult(Part.TWO, "123425"));
		assertEquals(12, Day01.getResult(Part.TWO, "123123"));
		assertEquals(4, Day01.getResult(Part.TWO, "12131415"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day01.getResult(Part.TWO, Day01.getInput(0));
		toConsole(result);
		assertEquals(1024, result);
	}
}
