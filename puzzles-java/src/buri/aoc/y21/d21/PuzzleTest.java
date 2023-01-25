package buri.aoc.y21.d21;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.common.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(739785L, Puzzle.getPart1Result(4, 8));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getPart1Result(8, 3);
		toConsole(result);
		assertEquals(412344L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(444356092776315L, Puzzle.getPart2Result(4, 8));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getPart2Result(8, 3);
		toConsole(result);
		assertEquals(214924284932572L, result);
	}
}