package buri.aoc.y17.d19;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 19: A Series of Tubes
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns input file as a diagram of the route.
	 */
	public static Diagram getInput(int fileIndex) {
		return (new Diagram(readFile(fileIndex)));
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