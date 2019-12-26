package buri.aoc.y16.d18;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 18: Like a Rogue
 * 
 * @author Brian Uri!
 */
public class Day18 extends BasePuzzle {

	/**
	 * Returns the input file unmodified. 
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2016/18", fileIndex).get(0));
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