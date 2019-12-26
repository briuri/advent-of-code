package buri.aoc.y17.d18;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day18Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day18.getInput(0);
		assertEquals(41, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(4, Day18.getResult(Part.ONE, Day18.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day18.getResult(Part.ONE, Day18.getInput(0));
		toConsole(result);
		assertEquals(2951, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Day18.getResult(Part.TWO, Day18.getInput(2)));
	}
	
	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day18.getResult(Part.TWO, Day18.getInput(0));
		toConsole(result);
		assertEquals(7366, result);
	}
}
