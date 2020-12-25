package buri.aoc.y16.d18;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 18: Like a Rogue
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile(fileIndex).get(0));
	}

	/**
	 * Part 1:
	 * Starting with the map in your puzzle input, in a total of 40 rows (including the starting row), how many safe
	 * tiles are there?
	 * 
	 * Part 2:
	 * How many safe tiles are there in a total of 400000 rows?
	 */
	public static int getResult(Part part, String input, int rows) {
		Grid grid = new Grid(input, rows);
		return (grid.getSafeCount(rows));
	}
}