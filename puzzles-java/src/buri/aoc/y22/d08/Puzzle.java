package buri.aoc.y22.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 08: Treetop Tree House
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(21L, 1, false);
		assertRun(1543L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(8L, 1, false);
		assertRun(595080L, 0, true);
	}

	/**
	 * Part 1:
	 * How many trees are visible from outside the grid?
	 *
	 * Part 2:
	 * What is the highest scenic score possible for any tree?
	 */
	protected long runLong(Part part, List<String> input) {
		Grid grid = new Grid(input);
		return (grid.getAnswer(part));
	}
}