package buri.aoc.y20.d02;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 02: Password Philosophy
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file with each policy on a line.
	 */
	public static List<Policy> getInput(int fileIndex) {
		List<Policy> list = new ArrayList<>();
		for (String input : readFile(fileIndex)) {
			list.add(new Policy(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * How many passwords are valid according to their policies?
	 *
	 * Part 2:
	 * How many passwords are valid according to the new interpretation of the policies?
	 */
	public static int getResult(Part part, List<Policy> input) {
		int valid = 0;
		for (Policy policy : input) {
			if (policy.isValid(part)) {
				valid++;
			}
		}
		return (valid);
	}
}