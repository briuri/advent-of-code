package buri.aoc.y18.d01;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The device shows a sequence of changes in frequency (your puzzle input). A value like +6 means the
 * current frequency increases by 6; a value like -3 means the current frequency decreases by 3.
 * 
 * @author Brian Uri!
 */
public class Day01 extends Puzzle {

	/**
	 * Input: One integer per line
	 * Output: A list of integers
	 */
	public static List<Integer> getInput(int fileIndex) {
		return (convertStringsToInts(readFile("2018/01", fileIndex)));
	}

	/**
	 * Part 1:
	 * Starting with a frequency of zero, what is the resulting frequency after all of the changes in frequency have
	 * been applied?
	 * 
	 * Part 2:
	 * You notice that the device repeats the same frequency change list over and over. To calibrate the device, you
	 * need to find the first frequency it reaches twice. Note that your device might need to repeat its list of
	 * frequency changes many times before a duplicate frequency is found, and that duplicates might be found while in
	 * the middle of processing the list. What is the first frequency your device reaches twice?
	 */
	public static int getResult(Part part, List<Integer> input) {
		Set<Integer> repeats = new HashSet<>();
		int sum = 0;
		while (true) {
			for (Integer value : input) {
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