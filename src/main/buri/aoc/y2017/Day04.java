package buri.aoc.y2017;

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
		/* No duplicate words */
		NO_DUPLICATES,
		/* No anagrams */
		NO_ANAGRAMS
	}
	
	/**
	 * Private to avoid instantiation.
	 */
	private Day04() {}
	
	/**
	 * Counts the number of passphrases that are valid.
	 * 
	 * @param passphrases a list of passphrases, each represented as a list of Strings
	 * @param strategy the strategy for determining valid passphrases
	 * @return number of passphrases
	 */
	public static int countValidPassphrases(List<List<String>> passphrases, Strategy strategy) {
		int validCount = 0;
		for (List<String> passphrase : passphrases) {
			if (isValid(passphrase, strategy)) {
				validCount++;
			}
		}
		return (validCount);
	}
	
	/**
	 * Checks for valid passphrases based on the strategy.
	 */
	protected static boolean isValid(List<String> passphrase, Strategy strategy) {
		Set<String> uniqueSet = new HashSet<>();
		for (String word : passphrase) {
			if (strategy == Strategy.NO_ANAGRAMS) {
				char[] sortedWord = word.toCharArray();
				Arrays.sort(sortedWord);
				word = new String(sortedWord);
			}
			// Default to NO_DUPLICATES
			
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
