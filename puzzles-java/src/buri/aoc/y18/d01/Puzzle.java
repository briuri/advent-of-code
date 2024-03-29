package buri.aoc.y18.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 1: Chronal Calibration
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(442L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(59908L, 0, true);
	}

	/**
	 * Part 1:
	 * Starting with a frequency of zero, what is the resulting frequency after all of the changes in frequency have
	 * been applied?
	 *
	 * Part 2:
	 * You notice that the device repeats the same frequency change list over and over. What is the first frequency your
	 * device reaches twice?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> values = PuzzleMath.toInts(input);
		Set<Integer> repeats = new HashSet<>();
		int sum = 0;
		while (true) {
			for (Integer value : values) {
				// Part 2: Stop as soon as a repeat occurs.
				if (part == Part.TWO && repeats.contains(sum)) {
					return (sum);
				}
				repeats.add(sum);
				sum += value;
			}
			// Part 1: Stop after one complete iteration.
			if (part == Part.ONE) {
				return (sum);
			}
		}
	}
}