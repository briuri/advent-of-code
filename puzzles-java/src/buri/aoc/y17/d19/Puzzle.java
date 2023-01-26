package buri.aoc.y17.d19;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 19: A Series of Tubes
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("ABCDEF", 1, false);
		assertRun("LXWCKGRAOY", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("38", 1, false);
		assertRun("17302", 0, true);
	}

	/**
	 * Part 1:
	 * What letters will it see (in the order it would see them) if it follows the path?
	 *
	 * Part 2:
	 * How many steps does the packet need to go?
	 */
	protected String runString(Part part, List<String> input) {
		Diagram diagram = new Diagram(input);
		String path = diagram.run();
		if (part == Part.ONE) {
			return (path);
		}
		return (String.valueOf(diagram.getSteps()));
	}
}