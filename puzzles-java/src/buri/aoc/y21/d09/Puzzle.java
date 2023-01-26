package buri.aoc.y21.d09;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 09: Smoke Basin
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(15L, 1, false);
		assertRun(468L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1134L, 1, false);
		assertRun(1280496L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the sum of the risk levels of all low points on your heightmap?
	 *
	 * Part 2:
	 * What do you get if you multiply together the sizes of the three largest basins?
	 */
	protected long runLong(Part part, List<String> input) {
		HeightMap grid = new HeightMap(input);
		if (part == Part.ONE) {
			return (grid.getRisk());
		}
		return (grid.getBasinProduct());
	}
}