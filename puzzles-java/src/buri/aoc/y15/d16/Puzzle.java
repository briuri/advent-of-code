package buri.aoc.y15.d16;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 16: Aunt Sue
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(40L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(241L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the number of the Sue that got you the gift?
	 *
	 * Part 2:
	 * What is the number of the real Aunt Sue?
	 */
	protected long runLong(Part part, List<String> input) {
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
			if (doesNotMatch(part, sue, "children", 3) || doesNotMatch(part, sue, "samoyeds", 2)
				|| doesNotMatch(part, sue, "akitas", 0) || doesNotMatch(part, sue, "vizslas", 0)
				|| doesNotMatch(part, sue, "cars", 2) || doesNotMatch(part, sue, "perfumes", 1)
				|| doesNotMatch(part, sue, "cats", 7) || doesNotMatch(part, sue, "trees", 3)
				|| doesNotMatch(part, sue, "pomeranians", 3) || doesNotMatch(part, sue, "goldfish", 5)) {
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
	private static boolean doesNotMatch(Part part, Map<String, Integer> sue, String name, Integer expected) {
		Integer value = sue.get(name);
		if (part == Part.TWO) {
			if (name.equals("cats") || name.equals("trees")) {
				return (value != null && value <= expected);
			}
			if (name.equals("pomeranians") || name.equals("goldfish")) {
				return (value != null && value >= expected);
			}
		}
		return (value != null && !value.equals(expected));
	}
}