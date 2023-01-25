package buri.aoc.y20.d09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Permutations;

/**
 * Day 09: Encoding Error
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the first number that does not have this property?
	 *
	 * Part 2:
	 * What is the encryption weakness in your XMAS-encrypted list of numbers?
	 */
	public static long getResult(Part part, List<String> input, int preambleSize) {
		List<Long> values = convertStringsToLongs(input);
		long target = 0;
		for (int i = preambleSize; i < input.size(); i++) {
			target = values.get(i);
			if (!isSummable(values.subList(i - preambleSize, i), target)) {
				if (part == Part.ONE) {
					return (target);
				}
				break;
			}
		}

		// Work backwards from end of list.
		int start = input.size() - 1;
		while (start > 0) {
			long sum = 0;
			for (int i = start; i >= start - preambleSize; i--) {
				sum += values.get(i).longValue();
				// Sum should have at least 2 addends.
				if (sum == target && i != start) {
					List<Long> range = new ArrayList<>();
					for (int j = i; j <= start; j++) {
						range.add(values.get(j));
					}
					Collections.sort(range);
					return (range.get(0) + range.get(range.size() - 1));
				}
				if (sum > target) {
					break;
				}
			}
			start--;
		}
		return (0);
	}

	/**
	 * Returns true if the target value can be summed from 2 numbers in the subList.
	 */
	protected static boolean isSummable(List<Long> subList, long target) {
		for (List<Long> pairs : Permutations.getPairPermutations(subList)) {
			if (pairs.get(0).longValue() + pairs.get(1).longValue() == target) {
				return (true);
			}
		}
		return (false);
	}
}