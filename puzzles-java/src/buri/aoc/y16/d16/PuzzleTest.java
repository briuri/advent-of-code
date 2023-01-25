package buri.aoc.y16.d16;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1GenerateCurve() {
		assertEquals("100", Puzzle.generateCurve("1", 3));
		assertEquals("001", Puzzle.generateCurve("0", 3));
		assertEquals("11111000000", Puzzle.generateCurve("11111", 11));
		assertEquals("1111000010100101011110000", Puzzle.generateCurve("111100001010", 25));
	}

	@Test
	public void testPart1GetChecksum() {
		assertEquals("100", Puzzle.getChecksum("110010110100"));
	}

	@Test
	public void testPart1Examples() {
		assertEquals("01100", Puzzle.getResult(Part.ONE, "10000", 20));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, "11011110011011101", 272);
		toConsole(result);
		assertEquals("00000100100001100", result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, "11011110011011101", 35651584);
		toConsole(result);
		assertEquals("00011010100010010", result);
	}
}