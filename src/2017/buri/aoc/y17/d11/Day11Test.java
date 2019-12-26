package buri.aoc.y17.d11;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day11Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day11.getInput(0);
		assertEquals(8223, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Day11.getResult(Part.ONE, Day11.getInput(1)));
		assertEquals(0, Day11.getResult(Part.ONE, Day11.getInput(2)));
		assertEquals(2, Day11.getResult(Part.ONE, Day11.getInput(3)));
		assertEquals(3, Day11.getResult(Part.ONE, Day11.getInput(4)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day11.getResult(Part.ONE, Day11.getInput(0));
		toConsole(result);
		assertEquals(877, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day11.getResult(Part.TWO, Day11.getInput(0));
		toConsole(result);
		assertEquals(1622, result);
	}
}
