package buri.aoc.y18.d02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 2: Inventory Management System
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the checksum for your list of box IDs?
	 *
	 * Part 2:
	 * What letters are common between the two correct box IDs?
	 */
	public static String getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			int twos = 0;
			int threes = 0;
			for (String id : input) {
				if (hasCharRepeats(id, 2)) {
					twos++;
				}
				if (hasCharRepeats(id, 3)) {
					threes++;
				}
			}
			return (String.valueOf(twos * threes));
		}
		// Part TWO
		for (String id1 : input) {
			for (String id2 : input) {
				String common = getCommonLetters(id1, id2);
				if (common.length() == id1.length() - 1) {
					return (common);
				}
			}
		}
		throw new RuntimeException("No IDs varied by exactly one character.");
	}

	/**
	 * Returns true if a string contains a character that repeats exactly num times.
	 */
	private static boolean hasCharRepeats(String id, int num) {
		Map<Character, Integer> charCounts = new HashMap<>();
		for (int i = 0; i < id.length(); i++) {
			char letter = id.charAt(i);
			charCounts.put(letter, charCounts.getOrDefault(letter, 0) + 1);
		}
		Set<Integer> uniques = new HashSet<>(charCounts.values());
		return (uniques.contains(num));
	}

	/**
	 * Compares two strings index by index and generates a list of common characters.
	 *
	 * Assumes strings are equal length.
	 */
	public static String getCommonLetters(String id1, String id2) {
		StringBuffer common = new StringBuffer();
		for (int i = 0; i < id1.length(); i++) {
			char testChar = id1.charAt(i);
			if (testChar == id2.charAt(i)) {
				common.append(testChar);
			}
		}
		return (common.toString());
	}
}