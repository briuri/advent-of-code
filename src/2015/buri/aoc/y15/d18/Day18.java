package buri.aoc.y15.d18;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day Day 18: Like a GIF For Your Yard
 * 
 * @author Brian Uri!
 */
public class Day18 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2015/18", fileIndex));
	}

	/**
	 * Part 1:
	 * Given your initial configuration, how many lights are on after 100 steps?
	 * 
	 * Part 2:
	 * Given your initial configuration, but with the four corners always in the on state, how many lights are on after
	 * 100 steps?
	 */
	public static int getResult(Part part, int steps, List<String> input) {
		Grid grid = new Grid(input);
		grid.animate(part, steps);
		return (grid.getLit());
	}
}