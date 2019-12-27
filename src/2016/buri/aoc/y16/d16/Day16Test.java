package buri.aoc.y16.d16;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test extends BaseTest {

	@Test
	public void testPart1GenerateCurve() {
		assertEquals("100", Day16.generateCurve("1", 3));
		assertEquals("001", Day16.generateCurve("0", 3));
		assertEquals("11111000000", Day16.generateCurve("11111", 11));
		assertEquals("1111000010100101011110000", Day16.generateCurve("111100001010", 25));
	}

	@Test
	public void testPart1GetChecksum() {
		assertEquals("100", Day16.getChecksum("110010110100"));
	}

	@Test
	public void testPart1Examples() {
		assertEquals("01100", Day16.getResult(Part.ONE, "10000", 20));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day16.getResult(Part.ONE, "11011110011011101", 272);
		toConsole(result);
		assertEquals("00000100100001100", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day16.getResult(Part.TWO, "11011110011011101", 35651584);
		toConsole(result);
		assertEquals("00011010100010010", result);
	}
}
