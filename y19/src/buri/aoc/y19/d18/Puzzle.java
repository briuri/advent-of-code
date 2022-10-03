package buri.aoc.y19.d18;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 18: Many-Worlds Interpretation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many steps is the shortest path that collects all of the keys?
	 *
	 * Part 2:
	 * After updating your map and using the remote-controlled robots, what is the fewest steps necessary to collect all
	 * of the keys?
	 */
	public static int getResult(Part part, List<String> input) {
		Vault vault = new Vault(input);
		return (vault.getFewestSteps());
	}
}