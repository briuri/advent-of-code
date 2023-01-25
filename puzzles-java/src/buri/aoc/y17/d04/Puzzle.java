package buri.aoc.y17.d04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 4: High-Entropy Passphrases
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(2, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle against the real input.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(466, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle against the real input.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(251, result);
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
	public static int getResult(Part part, List<String> input) {
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