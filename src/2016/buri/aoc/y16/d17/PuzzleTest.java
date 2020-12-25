package buri.aoc.y16.d17;

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
		assertEquals("DDRRRD", Puzzle.getResult(Part.ONE, "ihgpwlah"));
		assertEquals("DDUDRLRRUDRD", Puzzle.getResult(Part.ONE, "kglvqrro"));
		assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", Puzzle.getResult(Part.ONE, "ulqzkmiv"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, "rrrbmfta");
		toConsole(result);
		assertEquals("RLRDRDUDDR", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("370", Puzzle.getResult(Part.TWO, "ihgpwlah"));
		assertEquals("492", Puzzle.getResult(Part.TWO, "kglvqrro"));
		assertEquals("830", Puzzle.getResult(Part.TWO, "ulqzkmiv"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, "rrrbmfta");
		toConsole(result);
		assertEquals("420", result);
	}
}
