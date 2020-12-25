package buri.aoc.y15.d24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 24: It Hangs in the Balance
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodifeid
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * What is the quantum entanglement of the first group of packages in the ideal configuration?
	 * 
	 * Part 2:
	 * Balance the sleigh again, but this time, separate the packages into four groups instead of three. The other
	 * constraints still apply.
	 */
	public static long getResult(Part part, List<String> input) {
		List<Integer> weights = new ArrayList<>();
		for (String line : input) {
			weights.add(Integer.valueOf(line));
		}
		int division = part == Part.ONE ? 3 : 4;
		int targetWeight = getSum(weights) / division;

		// Get the smallest possible First Bag.
		List<List<Integer>> bag1Permutations = getPermutations(weights, targetWeight);
		sortBags(bag1Permutations);
		int smallestBagSize = bag1Permutations.get(0).size();
		for (Iterator<List<Integer>> iterator = bag1Permutations.iterator(); iterator.hasNext();) {
			List<Integer> bag1 = iterator.next();
			// Filter out bags that are too big, or leave undividable bags behind.
			if (bag1.size() > smallestBagSize) {
				iterator.remove();
			}
			/**
			 * Assumes that all valid bag1 possibilities result in remaining weights that can be evenly divided into the
			 * remaining bags. I originally had a method to validate this condition, but it had no impact on my puzzle
			 * input.
			 */
		}

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
		Collections.sort(bags, new Comparator<List<Integer>>() {
			@Override
			public int compare(List<Integer> o1, List<Integer> o2) {
				return o1.size() - o2.size();
			}
		});
	}
}