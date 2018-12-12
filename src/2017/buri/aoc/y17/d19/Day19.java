package buri.aoc.y17.d19;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day19 extends Puzzle {

	/**
	 * Input: Text diagram of path
	 * Output: Diagram object
	 */
	public static Diagram getInput(int fileIndex) {
		return (new Diagram(readFile("2017/19", fileIndex)));
	}

	/**
	 * Part 1:
	 * What letters will it see (in the order it would see them) if it follows the path?
	 * 
	 * Part 2:
	 * How many steps does the packet need to go?
	 */
	public static String getResult(Part part, Diagram input) {
		String path = input.run();
		if (part == Part.ONE) {
			return (path);
		}
		return (String.valueOf(input.getSteps()));
	}
}