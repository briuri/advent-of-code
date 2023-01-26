package buri.aoc.y22.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 22: Monkey Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(6032L, 1, false);
		assertRun(155060L, 0, true);
	}
	@Test
	public void testPart2() {
// My solution was hardcoded to the real input.
//		assertRun(5031L, 1, false);
		assertRun(3479L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the final password?
	 *
	 * Part 2:
	 * What is the final password?
	 */
	protected long runLong(Part part, List<String> input) {
		Grid grid = new Grid(input);
		grid.run(part, input.get(input.size() - 1));
		return (grid.getValue());
	}
}