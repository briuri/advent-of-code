package buri.aoc.y18.d05;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 5: Alchemical Reduction
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(10L, 1, false);
		assertRun(9686L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(4L, 1, false);
		assertRun(5524L, 0, true);
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
	protected long runLong(Part part, List<String> input) {
		String newPolymer = reactPolymer(input.get(0));
		if (part == Part.ONE) {
			return (newPolymer.length());
		}
		// Part TWO: Start with reduced polymer to improve performance.
		// Record all unique units.
		Set<Character> uniques = new HashSet<>();
		for (Character c : newPolymer.toLowerCase().toCharArray()) {
			uniques.add(c);
		}

		// Try removing each unit and reacting the polymer.
		int minSize = Integer.MAX_VALUE;
		for (Character polymer : uniques) {
			String regexp = String.format("[%c%c]", polymer, Character.toUpperCase(polymer));
			int size = reactPolymer(newPolymer.replaceAll(regexp, "")).length();
			minSize = Math.min(minSize, size);
		}
		return (minSize);
	}

	/**
	 * Parses the polymer, removing units of opposite polarity.
	 */
	private static String reactPolymer(String input) {
		StringBuilder polymer = new StringBuilder(input);
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