package buri.aoc.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for permutations.
 * 
 * @author Brian Uri!
 */
public class Permutations {

	/**
	 * Returns all possible pair permutations of a list of numbers.
	 */
	public static Set<List<Integer>> getPairPermutations(List<Integer> numbers) {
		Set<Set<Integer>> uniquePairs = new HashSet<>();
		for (Integer a : numbers) {
			for (Integer b : numbers) {
				if (!a.equals(b)) {
					Set<Integer> pair = new HashSet<>();
					pair.add(a);
					pair.add(b);
					uniquePairs.add(pair);
				}
			}
		}
		Set<List<Integer>> pairs = new HashSet<>();
		for (Set<Integer> pair : uniquePairs) {
			pairs.add(new ArrayList(pair));
		}
		return (pairs);
	}

	/**
	 * Returns all possible permutations of a list of numbers.
	 */
	public static List<int[]> getPermutations(int[] numbers) {
		final ArrayList<int[]> result = new ArrayList<int[]>();
		final int length = numbers.length;
		if (length == 0) {
			return result;
		}
		if (length == 1) {
			result.add(numbers);
			return result;
		}

		int[] subClone = Arrays.copyOf(numbers, length - 1);
		System.arraycopy(numbers, 1, subClone, 0, length - 1);

		for (int i = 0; i < length; ++i) {
			int e = numbers[i];
			if (i > 0) {
				subClone[i - 1] = numbers[0];
			}
			final List<int[]> subPermutations = getPermutations(subClone);
			for (int[] sc : subPermutations) {
				int[] clone = Arrays.copyOf(numbers, length);
				clone[0] = e;
				System.arraycopy(sc, 0, clone, 1, length - 1);
				result.add(clone);
			}
			if (i > 0) {
				subClone[i - 1] = e;
			}
		}
		return result;
	}
}
