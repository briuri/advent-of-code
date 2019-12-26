package buri.aoc.y15.d16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 16: Aunt Sue
 * 
 * @author Brian Uri!
 */
public class Day16 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2015/16", fileIndex));
	}

	/**
	 * Part 1:
	 * What is the number of the Sue that got you the gift?
	 * 
	 * Part 2:
	 * QUESTION
	 */
	public static int getResult(Part part, List<String> input) {
		List<Map<String, Integer>> sues = new ArrayList<>();
		for (String line : input) {
			Map<String, Integer> sue = new HashMap<>();
			String[] tokens = line.split(" ");
			for (int i = 2; i < tokens.length; i = i + 2) {
				sue.put(tokens[i], Integer.valueOf(tokens[i + 1]));
			}
			sues.add(sue);
		}

		for (Map<String, Integer> sue : sues) {
			if (!mightMatch(part, sue, "children", 3) || !mightMatch(part, sue, "samoyeds", 2) || !mightMatch(part, sue,
				"akitas", 0) || !mightMatch(part, sue, "vizslas", 0) || !mightMatch(part, sue, "cars", 2)
				|| !mightMatch(part, sue, "perfumes", 1) || !mightMatch(part, sue, "cats", 7) || !mightMatch(part, sue,
					"trees", 3) || !mightMatch(part, sue, "pomeranians", 3) || !mightMatch(part, sue, "goldfish", 5)) {
				sue.clear();
			}
		}
		for (int i = 0; i < sues.size(); i++) {
			if (sues.get(i).size() > 0) {
				return (i + 1);
			}
		}
		throw new RuntimeException("Could not find Sue.");
	}

	/**
	 * Returns true if Sue has a null value or it matches the expected value.
	 */
	private static boolean mightMatch(Part part, Map<String, Integer> sue, String name, Integer expected) {
		Integer value = sue.get(name);
		if (part == Part.TWO) {
			if (name.equals("cats") || name.equals("trees")) {
				return (value == null || value > expected);
			}
			if (name.equals("pomeranians") || name.equals("goldfish")) {
				return (value == null || value < expected);
			}
		}
		return (value == null || value.equals(expected));
	}
}