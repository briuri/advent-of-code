package buri.aoc.y22.d22;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

import java.util.List;

/**
 * Day 22: Monkey Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the final password?
	 *
	 * Part 2:
	 * What is the final password?
	 */
	public static long getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		grid.run(part, input.get(input.size() - 1));
		return (grid.getValue());
	}
}