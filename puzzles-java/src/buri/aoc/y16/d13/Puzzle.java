package buri.aoc.y16.d13;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;

/**
 * Day 13: A Maze of Twisty Little Cubicles
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the fewest number of steps required for you to reach 31,39?
	 * 
	 * Part 2:
	 * How many locations (distinct x,y coordinates, including your starting location) can you reach in at most 50
	 * steps?
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