package buri.aoc.y21.d09;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 09: Smoke Basin
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the sum of the risk levels of all low points on your heightmap?
	 *
	 * Part 2:
	 * What do you get if you multiply together the sizes of the three largest basins?
	 */
	public static long getResult(Part part, List<String> input) {
		HeightMap grid = new HeightMap(input);
		if (part == Part.ONE) {
			return (grid.getRisk());
		}
		return (grid.getBasinProduct());
	}
}