package buri.aoc.y19.d12;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day12Test extends BaseTest {

	@Test
	public void testGetInput() {
		assertEquals(4, Day12.getInput(0).size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(179, Day12.getResult(Part.ONE, Day12.getInput(1), 10));
		assertEquals(1940, Day12.getResult(Part.ONE, Day12.getInput(2), 100));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day12.getResult(Part.ONE, Day12.getInput(0), 1000);
		toConsole(result);
		assertEquals(8362, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2772, Day12.getResult(Part.TWO, Day12.getInput(1), 0));
		assertEquals(4686774924L, Day12.getResult(Part.TWO, Day12.getInput(2), 0));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day12.getResult(Part.TWO, Day12.getInput(0), 0);
		toConsole(result);
		assertEquals(478373365921244L, result);
	}
}
