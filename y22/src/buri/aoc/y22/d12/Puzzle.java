package buri.aoc.y22.d12;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

import java.util.List;

/**
 * Day 12: Hill Climbing Algorithm
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the fewest steps required to move from your current position to the location that should get the best signal?
	 *
	 * Part 2:
	 * What is the fewest steps required to move starting from any square with elevation a to the location that should get the best signal?
	 */
	public static long getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		return (grid.getSteps(part));
	}
}