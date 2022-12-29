package buri.aoc.y22.d25;

import buri.aoc.BaseTest;
import buri.aoc.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testConversion() {
		assertEquals("1=12=0202-000-=0", Puzzle.toSnafu(19925921840L));
		assertEquals(19925921840L, Puzzle.toLong("1=12=0202-000-=0"));
	}

	@Test
	public void testPart1Examples() {
		assertEquals("2=-1=0", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("2=001=-2=--0212-22-2", result);
	}
}