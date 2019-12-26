package buri.aoc.y19.d18;

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
		assertEquals(81, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(8, Day18.getResult(Part.ONE, Day18.getInput(1)));
		assertEquals(86, Day18.getResult(Part.ONE, Day18.getInput(2)));
		assertEquals(132, Day18.getResult(Part.ONE, Day18.getInput(3)));
		assertEquals(136, Day18.getResult(Part.ONE, Day18.getInput(4)));
		assertEquals(81, Day18.getResult(Part.ONE, Day18.getInput(5)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day18.getResult(Part.ONE, Day18.getInput(0));
		toConsole(result);
		assertEquals(5392, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8, Day18.getResult(Part.TWO, Day18.getInput(7)));
		assertEquals(24, Day18.getResult(Part.TWO, Day18.getInput(8)));
		assertEquals(32, Day18.getResult(Part.TWO, Day18.getInput(9)));
//		assertEquals(72, Day18.getResult(Part.TWO, Day18.getInput(10)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day18.getResult(Part.TWO, Day18.getInput(6));
		toConsole(result);
		assertEquals(1684, result);
	}
}
