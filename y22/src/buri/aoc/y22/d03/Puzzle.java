package buri.aoc.y22.d03;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 03: Rucksack Reorganization
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwyxzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Part 1:
	 * What is the sum of the priorities of those item types?
	 *
	 * Part 2:
	 * What is the sum of the priorities of those item types?
	 */
	public static long getResult(Part part, List<String> input) {
		List<List<String>> groups = new ArrayList<>();
		if (part == Part.ONE) {
			for (String line : input) {
				int half = line.length() / 2;
				List<String> group = new ArrayList<>();
				group.add(line.substring(0, half));
				group.add(line.substring(half));
				groups.add(group);
			}
		}
		else {
			int groupIndex = 0;
			while (groupIndex < input.size()) {
				List<String> group = new ArrayList<>();
				group.add(input.get(groupIndex));
				group.add(input.get(groupIndex + 1));
				group.add(input.get(groupIndex + 2));
				groups.add(group);
				groupIndex += 3;
			}
		}

		long sum = 0;
		for (List<String> group : groups) {
			for (int i = 0; i < group.get(0).length(); i++) {
				char value = group.get(0).charAt(i);
				if (isShared(group, value)) {
					sum += ALPHABET.indexOf(value) + 1;
					break;
				}
			}
		}
		return (sum);
	}

	/**
	 * Checks if a character appears in each member of the group.
	 */
	private static boolean isShared(List<String> group, char value) {
		boolean isShared = true;
		for (String sack : group) {
			isShared = (isShared && sack.indexOf(value) != -1);
		}
		return (isShared);
	}
}