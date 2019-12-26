package buri.aoc.y18.d22;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;
import buri.aoc.data.Pair;

/**
 * Day 22: Mode Maze
 * 
 * @author Brian Uri!
 */
public class Day22 extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the total risk level for the smallest rectangle that includes 0,0 and the target's coordinates?
	 * 
	 * Part 2:
	 * What is the fewest number of minutes you can take to reach the target?
	 */
	public static int getResult(Part part, int depth, Pair target) {
		Maze maze = new Maze(depth, target);
		return (part == Part.ONE ? maze.getRiskLevel() : maze.getMinutes());
	}
}