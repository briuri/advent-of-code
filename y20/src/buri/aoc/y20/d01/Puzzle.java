package buri.aoc.y20.d01;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 01: Report Repair
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * Find the two entries that sum to 2020; what do you get if you multiply them together?
	 *
	 * Part 2:
	 * In your expense report, what is the product of the three entries that sum to 2020?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Integer> values = convertStringsToInts(input);
		final int target = 2020;
		for (int i = 0; i < input.size(); i++) {
			int val1 = values.get(i);
			for (int j = 0; j < input.size(); j++) {
				if (i == j) {
					continue;
				}
				int val2 = values.get(j);
				if (part == Part.ONE && (val1 + val2) == target) {
					return (val1 * val2);
				}
				if (part == Part.TWO) {
					for (int k = 0; k < input.size(); k++) {
						if (i == k || j == k) {
							continue;
						}
						int val3 = values.get(k);
						if (val1 + val2 + val3 == target) {
							return (val1 * val2 * val3);
						}
					}
				}
			}
		}
		throw new RuntimeException("No values add up correctly.");
	}
}