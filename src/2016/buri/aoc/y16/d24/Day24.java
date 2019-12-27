package buri.aoc.y16.d24;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 24: Air Duct Spelunking
 * 
 * @author Brian Uri!
 */
public class Day24 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * Given your actual map, and starting from location 0, what is the fewest number of steps required to visit every
	 * non-0 number marked on the map at least once?
	 * 
	 * Part 2:
	 * What is the fewest number of steps required to start at 0, visit every non-0 number marked on the map at least
	 * once, and then return to 0?
	 */
	public static int getResult(Part part, List<String> input) {
		Ducts ducts = new Ducts(input);
		return (ducts.getFewestSteps(part));
	}
}