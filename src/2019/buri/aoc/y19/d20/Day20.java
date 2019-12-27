package buri.aoc.y19.d20;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 20: Donut Maze
 * 
 * @author Brian Uri!
 */
public class Day20 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * In your maze, how many steps does it take to get from the open tile marked AA to the open tile marked ZZ?
	 * 
	 * Part 2:
	 * In your maze, when accounting for recursion, how many steps does it take to get from the open tile marked AA to
	 * the open tile marked ZZ, both at the outermost layer?
	 */
	public static int getResult(Part part, List<String> input) {
		Maze maze = new Maze(input);
		return (maze.getSteps(part));
	}
}