package buri.aoc.y15.d24;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Day 24: It Hangs in the Balance
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(99L, 1, false);
		assertRun(10723906903L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(44L, 1, false);
		assertRun(74850409L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the quantum entanglement of the first group of packages in the ideal configuration?
	 *
	 * Part 2:
	 * Balance the sleigh again, but this time, separate the packages into four groups instead of three. The other
	 * constraints still apply.
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> weights = new ArrayList<>();
		for (String line : input) {
			weights.add(Integer.valueOf(line));
		}
		int division = part == Part.ONE ? 3 : 4;
		int targetWeight = PuzzleMath.getIntSum(weights) / division;

		// Get the smallest possible First Bag.
		List<List<Integer>> bag1Permutations = getPermutations(weights, targetWeight);
		sortBags(bag1Permutations);
		int smallestBagSize = bag1Permutations.get(0).size();
		// Filter out bags that are too big, or leave undividable bags behind.

		/*
		  Assumes that all valid bag1 possibilities result in remaining weights that can be evenly divided into the
		  remaining bags. I originally had a method to validate this condition, but it had no impact on my puzzle
		  input.
		 */
		bag1Permutations.removeIf(bag1 -> bag1.size() > smallestBagSize);

		long minQte = Long.MAX_VALUE;
		for (List<Integer> bag : bag1Permutations) {
			long qte = 1;
			for (Integer weight : bag) {
				qte *= weight;
			}
			minQte = Math.min(minQte, qte);
		}
		return (minQte);
	}

	/**
	 * Determines all possible ways to make a bag of presents at the target weight.
	 */
	private static List<List<Integer>> getPermutations(List<Integer> weights, int targetWeight) {
		List<List<Integer>> results = new ArrayList<>();
		for (int i = 0; i < weights.size(); i++) {
			if (weights.get(i) == targetWeight) {
				List<Integer> subBag = new ArrayList<>();
				subBag.add(weights.get(i));
				results.add(subBag);
			}
			else if (weights.get(i) <= targetWeight) {
				for (List<Integer> subBag : getPermutations(weights.subList(i + 1, weights.size()), targetWeight
					- weights.get(i))) {
					subBag.add(0, weights.get(i));
					results.add(subBag);
				}
			}
		}
		return (results);
	}

	/**
	 * Sorts inside each bag in descending order of weight. Sorts the bags themselves in ascending order of size.
	 */
	private static void sortBags(List<List<Integer>> bags) {
		for (List<Integer> bag : bags) {
			Collections.sort(bag);
			Collections.reverse(bag);
		}
		bags.sort(Comparator.comparingInt(List::size));
	}
}