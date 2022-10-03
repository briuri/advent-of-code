package buri.aoc.y19.d15;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.intcode.Computer;

/**
 * Day 15: Oxygen System
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the fewest number of movement commands required to move the repair droid from its starting position to
	 * the location of the oxygen system?
	 *
	 * Part 2:
	 * Use the repair droid to get a complete map of the area. How many minutes will it take to fill with oxygen?
	 */
	public static int getResult(Part part, List<String> input) {
		Maze maze = new Maze();
		Computer droid = new Computer(input);
		maze.explore(droid);
		if (part == Part.ONE) {
			return (maze.getFewestCommands());
		}
		return (maze.getSpreadTime());
	}
}