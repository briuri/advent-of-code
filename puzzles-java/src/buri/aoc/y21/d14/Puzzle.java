package buri.aoc.y21.d14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 14: Extended Polymerization
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(1588L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4244L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2188189693529L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4807056953866L, result);
	}

	/**
	 * Part 1:
	 * (10 rounds) What do you get if you take the quantity of the most common element and subtract the quantity of the
	 * least common element?
	 *
	 * Part 2:
	 * (40 rounds) What do you get if you take the quantity of the most common element and subtract the quantity of the
	 * least common element?
	 */
	public static long getResult(Part part, List<String> input) {
		String firstElement = "";
		String lastElement = "";
		Map<String, Long> pairCounts = new HashMap<>();
		Map<String, String> rules = new HashMap<>();
		for (String line : input) {
			if (pairCounts.isEmpty()) {
				// Represent the template as the first/last letter and the counts of each overlapping pair.
				firstElement = line.substring(0, 1);
				lastElement = line.substring(line.length() - 1);
				for (int i = 0; i < line.length() - 1; i++) {
					String pair = line.substring(i, i + 2);
					pairCounts.put(pair, pairCounts.getOrDefault(pair, 0L) + 1);
				}
			}
			else if (line.length() != 0) {
				String[] tokens = line.split(" -> ");
				rules.put(tokens[0], tokens[1].substring(0, 1));
			}
		}

		int total = (part == Part.ONE ? 10 : 40);
		for (int i = 0; i < total; i++) {
			pairCounts = grow(pairCounts, rules);
		}

		// Now reduce the pair counts into counts for each individual element.
		Map<String, Long> elementCounts = new HashMap<>();
		for (String pair : pairCounts.keySet()) {
			String element1 = pair.substring(0, 1);
			String element2 = pair.substring(1, 2);
			elementCounts.put(element1, elementCounts.getOrDefault(element1, 0L) + pairCounts.get(pair));
			elementCounts.put(element2, elementCounts.getOrDefault(element2, 0L) + pairCounts.get(pair));
		}
		// Since all overlapping letters are counted twice, add an extra first / last letter.
		elementCounts.put(firstElement, elementCounts.get(firstElement) + 1);
		elementCounts.put(lastElement, elementCounts.get(lastElement) + 1);

		// Each count is double what it should be, so divide by 2 after doing the math.
		return ((getMax(elementCounts).getValue() - getMin(elementCounts).getValue()) / 2);
	}

	/**
	 * Takes the current polymer (represented as counts of the pairs) and calculates the next counts.
	 */
	protected static Map<String, Long> grow(Map<String, Long> pairCounts, Map<String, String> rules) {
		Map<String, Long> newPairCounts = new HashMap<>();
		for (String pair : pairCounts.keySet()) {
			String insert = rules.get(pair);
			String newPair1 = pair.substring(0, 1) + insert;
			String newPair2 = insert + pair.substring(1, 2);
			newPairCounts.put(newPair1, newPairCounts.getOrDefault(newPair1, 0L) + pairCounts.get(pair));
			newPairCounts.put(newPair2, newPairCounts.getOrDefault(newPair2, 0L) + pairCounts.get(pair));
		}
		return (newPairCounts);
	}
}