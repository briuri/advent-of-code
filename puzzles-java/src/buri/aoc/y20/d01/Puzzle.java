package buri.aoc.y20.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.List;

/**
 * Day 01: Report Repair
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(514579L, 1, false);
		assertRun(138379L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(241861950L, 1, false);
		assertRun(85491920L, 0, true);
	}

	/**
	 * Part 1:
	 * Find the two entries that sum to 2020; what do you get if you multiply them together?
	 *
	 * Part 2:
	 * In your expense report, what is the product of the three entries that sum to 2020?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> values = PuzzleMath.toInts(input);
		final int target = 2020;
		for (int i = 0; i < input.size(); i++) {
			int val1 = values.get(i);
			for (int j = 0; j < input.size(); j++) {
				if (i == j) {
					continue;
				}
				int val2 = values.get(j);
				if (part == Part.ONE && (val1 + val2) == target) {
					return ((long) val1 * val2);
				}
				if (part == Part.TWO) {
					for (int k = 0; k < input.size(); k++) {
						if (i == k || j == k) {
							continue;
						}
						int val3 = values.get(k);
						if (val1 + val2 + val3 == target) {
							return ((long) val1 * val2 * val3);
						}
					}
				}
			}
		}
		throw new RuntimeException("No values add up correctly.");
	}
}