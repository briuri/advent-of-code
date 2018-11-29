package buri.aoc.y17.d04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * A passphrase consists of a series of words (lowercase letters) separated by spaces.
 * 
 * The rules for a valid passphrase vary by puzzle part.
 * 
 * @author Brian Uri!
 */
public class Day04 extends Puzzle {

	/**
	 * Input: Multiple rows of space-delimited strings.
	 * Output: Rows of strings.
	 */
	public static List<List<String>> getInput(int fileIndex) {
		List<List<String>> rows = new ArrayList<>();
		for (String rawRow : readFile("2017/04", fileIndex)) {
			rows.add(Arrays.asList(rawRow.split(" ")));
		}
		return (rows);
	}
	
	/**
	 * Counts the number of passphrases that are valid.
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
