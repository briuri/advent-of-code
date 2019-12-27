package buri.aoc.y18.d14;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day14Test extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals("0124515891", Day14.getResult(Part.ONE, "5"));
		assertEquals("5158916779", Day14.getResult(Part.ONE, "9"));
		assertEquals("9251071085", Day14.getResult(Part.ONE, "18"));
		assertEquals("5941429882", Day14.getResult(Part.ONE, "2018"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day14.getResult(Part.ONE, "652601");
		toConsole(result);
		assertEquals("1221283494", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("5", Day14.getResult(Part.TWO, "01245"));
		assertEquals("9", Day14.getResult(Part.TWO, "51589"));
		assertEquals("18", Day14.getResult(Part.TWO, "92510"));
		assertEquals("2018", Day14.getResult(Part.TWO, "59414"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day14.getResult(Part.TWO, "652601");
		toConsole(result);
		assertEquals("20261485", result);
	}
}
