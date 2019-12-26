package buri.aoc.y16.d04;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day04Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day04.getInput(0);
		assertEquals(935, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(1514, Day04.getResult(Part.ONE, Day04.getInput(1)));
	}
	
	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, Day04.getInput(0));
		toConsole(result);
		assertEquals(137896, result);
	}
	
	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, Day04.getInput(0));
		toConsole(result);
		assertEquals(501, result);
	}
}
