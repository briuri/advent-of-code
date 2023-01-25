package buri.aoc.y16.d24;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 24: Air Duct Spelunking
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(14, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(490, result);
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(744, result);
	}

	/**
	 * Part 1:
	 * Given your actual map, and starting from location 0, what is the fewest number of steps required to visit every
	 * non-0 number marked on the map at least once?
	 *
	 * Part 2:
	 * What is the fewest number of steps required to start at 0, visit every non-0 number marked on the map at least
	 * once, and then return to 0?
	 */
	public static int getResult(Part part, List<String> input) {
		Ducts ducts = new Ducts(input);
		return (ducts.getFewestSteps(part));
	}
}