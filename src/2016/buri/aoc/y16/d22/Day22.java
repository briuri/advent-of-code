package buri.aoc.y16.d22;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 22: Grid Computing
 * 
 * @author Brian Uri!
 */
public class Day22 extends Puzzle {

	/**
	 * Returns the input file unmodified. 
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/22", fileIndex));
	}
	
	/**
	 * Part 1:
	 * How many viable pairs of nodes are there?
	 * 
	 * Part 2:
	 * Your goal is to gain access to the data which begins in the node with y=0 and the highest x (that is, the node in
	 * the top-right corner). What is the fewest number of steps required to move your goal data to node-x0-y0?
	 */
	public static int getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		if (part == Part.ONE) {
			return (grid.getViablePairs());
		}
		// Part TWO
		return (0);
	}
}