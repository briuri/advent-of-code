package buri.aoc.y16.d25;

import static org.junit.Assert.*;

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
		List<String> input = Day25.getInput(0);
		assertEquals(30, input.size());
	}

	@Test
	public void testInputConversion() {
		Registers.convertInput(Day25.getInput(0));
	}
	
	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day25.getResult(Part.ONE, Day25.getInput(0));
		toConsole(result);
		assertEquals(175, result);
	}
}
