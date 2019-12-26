package buri.aoc.y17.d04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 4: High-Entropy Passphrases
 * 
 * @author Brian Uri!
 */
public class Day04 extends BasePuzzle {

	/**
	 * Returns input file as a 2D list of strings.
	 */
	public static List<List<String>> getInput(int fileIndex) {
		List<List<String>> rows = new ArrayList<>();
		for (String rawRow : readFile("2017/04", fileIndex)) {
			rows.add(Arrays.asList(rawRow.split(" ")));
		}
		return (rows);
	}
	
	/**
	 * A passphrase consists of a series of words (lowercase letters) separated by spaces.
	 * 
	 * Part 1:
	 * A valid passphrase must contain no duplicate words. How many passphrases are valid?
	 * 
	 * Part 2:
	 * A valid passphrase must contain no two words that are anagrams of each other. How many passphrases are valid?
	 */
	public static int getResult(Part part, List<List<String>> passphrases) {
		int validCount = 0;
		for (List<String> passphrase : passphrases) {
			if (isValid(part, passphrase)) {
				validCount++;
			}
		}
		return (validCount);
	}

	/**
	 * Checks for valid passphrases based on the Part-specific rules.
	 */
	private static boolean isValid(Part part, List<String> passphrase) {
		Set<String> uniqueSet = new HashSet<>();
		for (String word : passphrase) {
			if (part == Part.TWO) {
				char[] sortedWord = word.toCharArray();
				Arrays.sort(sortedWord);
				word = new String(sortedWord);
			}

			if (!uniqueSet.contains(word)) {
				uniqueSet.add(word);
			}
			else {
				return (false);
			}
		}
		return (true);
	}
}
