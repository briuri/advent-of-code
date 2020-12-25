package buri.aoc.y15.d05;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 5: Doesn't He Have Intern-Elves For This?
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final Pattern REPEATING_CHAR = Pattern.compile("(\\w)\\1+");

	private final static List<String> FORBIDDEN_PAIRS = new ArrayList<>();

	static {
		FORBIDDEN_PAIRS.add("ab");
		FORBIDDEN_PAIRS.add("cd");
		FORBIDDEN_PAIRS.add("pq");
		FORBIDDEN_PAIRS.add("xy");
	}

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * How many strings are nice?
	 * 
	 * Part 2:
	 * How many strings are nice under these new rules?
	 */
	public static int getResult(Part part, List<String> input) {
		int nice = 0;
		if (part == Part.ONE) {
			for (String string : input) {
				if (hasVowels(string) && hasRepeatingChar(string) && hasNoForbiddenPairs(string)) {
					nice++;
				}
			}
		}
		else { // Part TWO
			for (String string : input) {
				if (hasRepeatingPair(string) && hasRepeatingCharInterrupted(string)) {
					nice++;
				}
			}
		}
		return (nice);
	}

	/**
	 * Returns true if a string contains at least 3 vowels.
	 */
	private static boolean hasVowels(String string) {
		String noVowels = string.replaceAll("[aeiou]", "");
		return (string.length() - noVowels.length() >= 3);
	}

	/**
	 * Returns true if a string contains a letter repeating twice in a row.
	 */
	private static boolean hasRepeatingChar(String string) {
		return (REPEATING_CHAR.matcher(string).find());
	}

	/**
	 * Returns true if a string contains no forbidden pairs.
	 */
	private static boolean hasNoForbiddenPairs(String string) {
		boolean hasNoPairs = true;
		for (String pair : FORBIDDEN_PAIRS) {
			hasNoPairs = hasNoPairs && string.indexOf(pair) == -1;
		}
		return (hasNoPairs);
	}

	/**
	 * Returns true if a pair of letters repeats without overlapping.
	 */
	private static boolean hasRepeatingPair(String string) {
		for (int i = 0; i < string.length() - 3; i++) {
			String pair = string.substring(i, i + 2);
			if (string.indexOf(pair, i + 2) != -1) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Returns true if a letter repeats with a single letter of any kind in the middle.
	 */
	private static boolean hasRepeatingCharInterrupted(String string) {
		for (int i = 0; i < string.length() - 2; i++) {
			char value = string.charAt(i);
			if (value == string.charAt(i + 2)) {
				return (true);
			}
		}
		return (false);
	}

}