package buri.aoc.y18.d01;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 1: Chronal Calibration
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * Starting with a frequency of zero, what is the resulting frequency after all of the changes in frequency have
	 * been applied?
	 *
	 * Part 2:
	 * You notice that the device repeats the same frequency change list over and over. What is the first frequency your
	 * device reaches twice?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Integer> values = convertStringsToInts(input);
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