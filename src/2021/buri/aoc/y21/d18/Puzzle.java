package buri.aoc.y21.d18;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 18: Snailfish
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the magnitude of the final sum?
	 *
	 * Part 2:
	 * What is the largest magnitude of any sum of two different snailfish numbers from the homework assignment?
	 */
	public static long getResult(Part part, List<String> input) {
		Number finalSum = new Number(input.get(0), null);
		for (int i = 1; i < input.size(); i++) {
			finalSum = new Number(finalSum, new Number(input.get(i), null));
		}
		if (part == Part.ONE) {
			return (finalSum.getMagnitude());
		}

		long maxMagnitude = Long.MIN_VALUE;
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < input.size(); j++) {
				if (i == j) {
					continue;
				}
				Number sum = new Number(new Number(input.get(i), null), new Number(input.get(j), null));
				maxMagnitude = Math.max(maxMagnitude, sum.getMagnitude());
				sum = new Number(new Number(input.get(j), null), new Number(input.get(i), null));
				maxMagnitude = Math.max(maxMagnitude, sum.getMagnitude());
			}
		}
		return (maxMagnitude);
	}
}