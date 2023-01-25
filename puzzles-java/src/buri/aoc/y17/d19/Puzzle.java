package buri.aoc.y17.d19;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 19: A Series of Tubes
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("ABCDEF", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("LXWCKGRAOY", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("38", Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("17302", result);
	}

	/**
	 * Part 1:
	 * What letters will it see (in the order it would see them) if it follows the path?
	 *
	 * Part 2:
	 * How many steps does the packet need to go?
	 */
	public static String getResult(Part part, List<String> input) {
		Diagram diagram = new Diagram(input);
		String path = diagram.run();
		if (part == Part.ONE) {
			return (path);
		}
		return (String.valueOf(diagram.getSteps()));
	}
}