package buri.aoc.y2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A passphrase consists of a series of words (lowercase letters) separated by spaces.
 * 
 * To ensure security, a valid passphrase must contain no duplicate words.
 * 
 * @author Brian Uri!
 */
public class Day04 {

	public enum Strategy {
		/* Part 1: No duplicate words */
		NO_DUPLICATES,
		/* Part 2: No anagrams */
		NO_ANAGRAMS
	}

	/**
	 * Loads the file at the provided path and returns its contents as a List of Strings.
	 * 
	 * @throws IllegalArgumentException on file I/O issues
	 */
	public static List<List<String>> getPassphrasesFromFile(String filePath) {
		List<List<String>> rows = new ArrayList<>();
		try {
			for (String rawRow : Files.readAllLines(Paths.get(filePath))) {
				List<String> tokens = new ArrayList<>();
				for (String token : Arrays.asList(rawRow.split(" "))) {
					tokens.add(token);
				}
				rows.add(tokens);
			}
			return (rows);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}

	/**
	 * Private to avoid instantiation.
	 */
	private Day04() {}

	/**
	 * Counts the number of passphrases that are valid.
	 * 
	 * @param strategy the strategy for determining valid passphrases
	 * @param passphrases a list of passphrases, each represented as a list of Strings
	 * @return number of passphrases that are valid
	 */
	public static int getValidCount(Strategy strategy, List<List<String>> passphrases) {
		int validCount = 0;
		for (List<String> passphrase : passphrases) {
			if (isValid(strategy, passphrase)) {
				validCount++;
			}
		}
		return (validCount);
	}

	/**
	 * Checks for valid passphrases based on the strategy.
	 * 
	 * @param strategy the strategy for determining valid passphrases
	 * @param passphrase a single passphrase
	 * @return true if valid, false otherwise
	 */
	private static boolean isValid(Strategy strategy, List<String> passphrase) {
		Set<String> uniqueSet = new HashSet<>();
		for (String word : passphrase) {
			if (strategy == Strategy.NO_ANAGRAMS) {
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
