package buri.aoc.y20.d09;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Permutations;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day 09: Encoding Error
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(127L, 1, false);
		assertRun(1930745883L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(62L, 1, false);
		assertRun(268878261L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the first number that does not have this property?
	 *
	 * Part 2:
	 * What is the encryption weakness in your XMAS-encrypted list of numbers?
	 */
	protected long runLong(Part part, List<String> input) {
		// Example has size 5, real input has 25.
		int preambleSize = (input.size() > 20 ? 25 : 5);
		List<Long> values = PuzzleMath.toLongs(input);
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
				sum += values.get(i);
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
			if (pairs.get(0) + pairs.get(1) == target) {
				return (true);
			}
		}
		return (false);
	}
}