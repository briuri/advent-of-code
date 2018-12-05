package buri.aoc.y18.d05;

import java.util.HashSet;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The polymer is formed by smaller units which, when triggered, react with each other such that two adjacent units of
 * the same type and opposite polarity are destroyed. Units' types are represented by letters; units' polarity is
 * represented by capitalization. For instance, r and R are units with the same type but opposite polarity, whereas r
 * and s are entirely different types and do not react.
 * 
 * @author Brian Uri!
 */
public class Day05 extends Puzzle {

	/**
	 * Input: The whole file is a polymer.
	 * Output: A long string.
	 */
	public static String getInput(int fileIndex) {
		return readFile("2018/05", fileIndex).get(0);
	}

	/**
	 * Part 1:
	 * How many units remain after fully reacting the polymer you scanned?
	 * 
	 * Part 2:
	 * One of the unit types is causing problems; it's preventing the polymer from collapsing as much as it should. Your
	 * goal is to figure out which unit type is causing the most problems, remove all instances of it (regardless of
	 * polarity), fully react the remaining polymer, and measure its length. What is the length of the shortest polymer
	 * you can produce by removing all units of exactly one type and fully reacting the result?
	 * 
	 */
	public static int getResult(Part part, String input) {
		String newPolymer = reactPolymer(input);
		if (part == Part.ONE) {
			return (newPolymer.length());
		}
		// Part TWO: Start with reduced polymer to improve performance.
		// Record all unique units.
		Set<String> uniques = new HashSet<>();
		for (Character c : newPolymer.toLowerCase().toCharArray()) {
			uniques.add(String.valueOf(c));
		}

		// Try removing each unit and reacting the polymer.
		Integer minSize = Integer.MAX_VALUE;
		for (String polymer : uniques) {
			String regexp = String.format("[%s%s]", polymer, polymer.toUpperCase());
			int size = reactPolymer(newPolymer.replaceAll(regexp, "")).length();
			minSize = Math.min(minSize, size);
		}
		return (minSize);
	}

	/**
	 * Parses the polymer, removing units of opposite polarity.
	 */
	private static String reactPolymer(String input) {
		StringBuffer polymer = new StringBuffer(input);
		for (int i = 0; i < polymer.length() - 1; /* no default increment */) {
			char first = polymer.charAt(i);
			char second = polymer.charAt(i + 1);
			char expected = (Character.isLowerCase(first) ? Character.toUpperCase(first)
				: Character.toLowerCase(first));
			if (second == expected) {
				// React and back up one unit.
				polymer.delete(i, i + 2);
				i = Math.max(0, i - 1);
			}
			else {
				// Proceed to next unit.
				i = i + 1;
			}
		}
		return polymer.toString();
	}
}