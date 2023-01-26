package buri.aoc.y22.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 12: Hill Climbing Algorithm
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(31L, 1, false);
		assertRun(490L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(29L, 1, false);
		assertRun(488L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the fewest steps required to move from your current position to the location that should get the best signal?
	 *
	 * Part 2:
	 * What is the fewest steps required to move starting from any square with elevation a to the location that should get the best signal?
	 */
	protected long runLong(Part part, List<String> input) {
		Grid grid = new Grid(input);
		return (grid.getSteps(part));
	}
}