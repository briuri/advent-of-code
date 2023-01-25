package buri.aoc.y22.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

import java.util.List;

/**
 * Day 08: Treetop Tree House
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many trees are visible from outside the grid?
	 *
	 * Part 2:
	 * What is the highest scenic score possible for any tree?
	 */
	public static long getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		return (grid.getAnswer(part));
	}
}