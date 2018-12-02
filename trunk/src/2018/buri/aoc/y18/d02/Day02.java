package buri.aoc.y18.d02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day02 extends Puzzle {

	/**
	 * Input: List of string IDs, one per line
	 * Output: List of strings.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/02", fileIndex));
	}
	
	/**
	 * Part 1:
	 * To make sure you didn't miss any, you scan the likely candidate boxes again, counting the number that have an ID
	 * containing exactly two of any letter and then separately counting those with exactly three of any letter. You can
	 * multiply those two counts together to get a rudimentary checksum and compare it to what your device predicts.
	 * 
	 * Part 2:
	 * The boxes will have IDs which differ by exactly one character at the same position in both strings.
	 */
	public static String getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			int twos = 0;
			int threes = 0;
			for (String id : input) {
				if (hasExactOccurrences(id, 2)) {
					twos++;
				}
				if (hasExactOccurrences(id, 3)) {
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
		throw new RuntimeException("No ids varied by exactly one character.");
	}
	
	/**
	 * Returns true if a string contains a character that repeats exactly num times.
	 */
	private static boolean hasExactOccurrences(String id, int num) {
		Map<Character, Integer> occurrences = new HashMap<>();
		for (int i = 0; i < id.length(); i++) {
			char letter = id.charAt(i);
			if (occurrences.get(letter) == null) {
				occurrences.put(letter, 0);
			}
			occurrences.put(letter, occurrences.get(letter) + 1);
		}
		Set<Integer> uniques = new HashSet<>();
		for (Integer value : occurrences.values()) {
			uniques.add(value);
		}
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