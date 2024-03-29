package buri.aoc.y17.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 4: High-Entropy Passphrases
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(2L, 1, false);
		assertRun(466L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(3L, 2, false);
		assertRun(251L, 0, true);
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
	protected long runLong(Part part, List<String> input) {
		List<List<String>> passphrases = new ArrayList<>();
		for (String line : input) {
			passphrases.add(Arrays.asList(line.split(" ")));
		}

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