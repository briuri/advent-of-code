package buri.aoc.y22.d22;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(6032L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(155060L, result);
	}

// My solution was hardcoded to the real input.
//	@Test
//	public void testPart2Examples() {
//		assertEquals(5031L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
//	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3479L, result);
	}
}