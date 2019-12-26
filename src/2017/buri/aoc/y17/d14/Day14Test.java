package buri.aoc.y17.d14;

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
		assertEquals(8108, Day14.getResult(Part.ONE, "flqrgnkx"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day14.getResult(Part.ONE, "jxqlasbh");
		toConsole(result);
		assertEquals(8140, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1242, Day14.getResult(Part.TWO, "flqrgnkx"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day14.getResult(Part.TWO, "jxqlasbh");
		toConsole(result);
		assertEquals(1182, result);
	}
}
