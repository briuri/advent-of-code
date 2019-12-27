package buri.aoc.y16.d13;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;
import buri.aoc.data.Pair;

/**
 * Day 13: A Maze of Twisty Little Cubicles
 * 
 * @author Brian Uri!
 */
public class Day13 extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the fewest number of steps required for you to reach 31,39?
	 * 
	 * Part 2:
	 * QUESTION
	 */
	public static int getResult(Part part, int magicNumber, Pair destination) {
		Grid grid = new Grid(magicNumber);
		if (part == Part.ONE) {
			return (grid.getStepsTo(destination));
		}
		// Part TWO
		return (grid.getClosePositions(50));
	}
}