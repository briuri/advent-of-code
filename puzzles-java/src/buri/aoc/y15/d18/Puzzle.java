package buri.aoc.y15.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day Day 18: Like a GIF For Your Yard
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(821L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(886L, 0, true);
	}

	/**
	 * Part 1:
	 * Given your initial configuration, how many lights are on after 100 steps?
	 *
	 * Part 2:
	 * Given your initial configuration, but with the four corners always in the on state, how many lights are on after
	 * 100 steps?
	 */
	protected long runLong(Part part, List<String> input) {
		Grid grid = new Grid(input);
		grid.animate(part, 100);
		return (grid.getLit());
	}
}