package buri.aoc.y19.d25;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day25Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day25.getInput(0);
		assertEquals(4806, input.size());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() throws IOException {
		String result = Day25.getResult(Part.ONE, Day25.getInput(0));
		toConsole(result);
		assertEquals("134227456", result);
	}
}
