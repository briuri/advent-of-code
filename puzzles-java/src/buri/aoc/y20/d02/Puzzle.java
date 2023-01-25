package buri.aoc.y20.d02;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 02: Password Philosophy
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many passwords are valid according to their policies?
	 *
	 * Part 2:
	 * How many passwords are valid according to the new interpretation of the policies?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Policy> policies = new ArrayList<>();
		for (String line : input) {
			policies.add(new Policy(line));
		}

		int valid = 0;
		for (Policy policy : policies) {
			if (policy.isValid(part)) {
				valid++;
			}
		}
		return (valid);
	}
}