package buri.aoc.y17.d22;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 22: Sporifica Virus
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * Given your actual map, after 10000 bursts of activity, how many bursts cause a node to become infected? (Do not
	 * count nodes that begin infected.)
	 *
	 * Part 2:
	 * Given your actual map, after 10000000 bursts of activity, how many bursts cause a node to become infected? (Do
	 * not count nodes that begin infected.)
	 */
	public static int getResult(Part part, List<String> input, int bursts) {
		Cluster cluster = new Cluster(input);
		for (int i = 1; i <= bursts; i++) {
			cluster.move(part);
		}
		return (cluster.getInfections());
	}
}