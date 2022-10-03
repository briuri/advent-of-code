package buri.aoc.y21.d01;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 01: Sonar Sweeep
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many measurements are larger than the previous measurement?
	 *
	 * Part 2:
	 * Consider sums of a three-measurement sliding window. How many sums are larger than the previous sum?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Integer> values = convertStringsToInts(input);
		long sum = 0;
		int windowSize = (part == Part.ONE ? 1 : 3);
		for (int i = windowSize; i < values.size(); i++) {
			// In part 2, simplify (B + C + D > A + B + C) to (D > A)
			if (values.get(i) > values.get(i - windowSize)) {
				sum++;
			}
		}
		return (sum);
	}
}