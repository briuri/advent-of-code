package buri.aoc.y18.d14;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals("0124515891", Puzzle.getResult(Part.ONE, "5"));
		assertEquals("5158916779", Puzzle.getResult(Part.ONE, "9"));
		assertEquals("9251071085", Puzzle.getResult(Part.ONE, "18"));
		assertEquals("5941429882", Puzzle.getResult(Part.ONE, "2018"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, "652601");
		toConsole(result);
		assertEquals("1221283494", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("5", Puzzle.getResult(Part.TWO, "01245"));
		assertEquals("9", Puzzle.getResult(Part.TWO, "51589"));
		assertEquals("18", Puzzle.getResult(Part.TWO, "92510"));
		assertEquals("2018", Puzzle.getResult(Part.TWO, "59414"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, "652601");
		toConsole(result);
		assertEquals("20261485", result);
	}
}
