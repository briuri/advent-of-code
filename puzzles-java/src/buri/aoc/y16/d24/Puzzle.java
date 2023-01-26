package buri.aoc.y16.d24;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 24: Air Duct Spelunking
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(14L, 1, false);
		assertRun(490L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(744L, 0, true);
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
	protected long runLong(Part part, List<String> input) {
		Ducts ducts = new Ducts(input);
		return (ducts.getFewestSteps(part));
	}
}