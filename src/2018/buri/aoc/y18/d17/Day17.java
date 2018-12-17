package buri.aoc.y18.d17;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day17 extends Puzzle {

	/**
	 * Input: A list of ranges denoting CLAY sections in the ground.
	 * Output: A list of strings, unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/17", fileIndex));
	}
	
	/**
	 * Part 1:
	 * How many tiles can the water reach within the range of y values in your scan?
	 * 
	 * Part 2:
	 * How many water tiles are left after the water spring stops producing water and all remaining water not at rest
	 * has drained?
	 */
	public static int getResult(Part part, List<String> input) {
		Reservoir reservoir = new Reservoir(input);
		return (reservoir.flow(part));
	}
}