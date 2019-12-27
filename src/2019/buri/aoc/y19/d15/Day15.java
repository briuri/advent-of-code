package buri.aoc.y19.d15;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.y19.intcode.Computer;
import buri.aoc.BasePuzzle;

/**
 * Day 15: Oxygen System
 * 
 * @author Brian Uri!
 */
public class Day15 extends BasePuzzle {

	/**
	 * Returns the input file as an Intcode program
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/15", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * What is the fewest number of movement commands required to move the repair droid from its starting position to
	 * the location of the oxygen system?
	 * 
	 * Part 2:
	 * Use the repair droid to get a complete map of the area. How many minutes will it take to fill with oxygen?
	 */
	public static int getResult(Part part, List<Long> program) {
		Maze maze = new Maze();
		Computer droid = new Computer(program);
		maze.explore(droid);
		if (part == Part.ONE) {
			return (maze.getFewestCommands());
		}
		return (maze.getSpreadTime());
	}
}