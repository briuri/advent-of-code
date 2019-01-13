package buri.aoc.y15.d17;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 17: No Such Thing as Too Much
 * 
 * @author Brian Uri!
 */
public class Day17 extends Puzzle {

	/**
	 * Returns the input file unmodified
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2015/17", fileIndex));
	}

	/**
	 * Part 1:
	 * Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of
	 * eggnog?
	 * 
	 * Part 2:
	 * QUESTION
	 */
	public static int getResult(Part part, int end, List<String> input) {
		List<Integer> containers = new ArrayList<>();
		for (String line : input) {
			containers.add(Integer.valueOf(line));
		}
		Collections.sort(containers);
		Collections.reverse(containers);

		Map<Integer, Integer> frequency = new HashMap<>();
		int combos = getCombos(0, end, containers, 0, frequency);
		if (part == Part.ONE) {
			return (combos);
		}

		// Part TWO
		int minContainers = Integer.MAX_VALUE;
		for (Integer key : frequency.keySet()) {
			minContainers = Math.min(minContainers, key);
		}
		return (frequency.get(minContainers));
	}

	/**
	 * Recursively figure out how many combinations can be made to get to the target value.
	 */
	private static int getCombos(int start, int end, List<Integer> choices, int depth,
		Map<Integer, Integer> frequency) {
		if (start > end) {
			return (0);
		}
		if (start == end) {
			frequency.put(depth, frequency.getOrDefault(depth, 0) + 1);
			return (1);
		}
		int combos = 0;
		for (int i = 0; i < choices.size(); i++) {
			combos += getCombos(start + choices.get(i), end, choices.subList(i + 1, choices.size()), depth + 1,
				frequency);
		}
		return (combos);
	}
}