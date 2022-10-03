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
	 * Private to prevent instantiation.
	 */
	private Permutations() {}
	
	/**
	 * Returns all possible pair permutations of a list of objects.
	 */
	public static <T> Set<List<T>> getPairPermutations(List<T> objects) {
		Set<Set<T>> uniquePairs = new HashSet<>();
		for (T a : objects) {
			for (T b : objects) {
				if (!a.equals(b)) {
					Set<T> pair = new HashSet<>();
					pair.add(a);
					pair.add(b);
					uniquePairs.add(pair);
				}
			}
		}
		// After uniqueness is confirmed, convert to lists for ease of traversal.
		Set<List<T>> pairs = new HashSet<>();
		for (Set<T> pair : uniquePairs) {
			pairs.add(new ArrayList(pair));
		}
		return (pairs);
	}

	/**
	 * Returns all possible permutations of a list of objects.
	 */
	public static <T> List<T[]> getPermutations(T[] objects) {
		final ArrayList<T[]> result = new ArrayList<T[]>();
		final int length = objects.length;
		if (length == 0) {
			return (result);
		}
		if (length == 1) {
			result.add(objects);
			return (result);
		}

		T[] subClone = Arrays.copyOf(objects, length - 1);
		System.arraycopy(objects, 1, subClone, 0, length - 1);

		for (int i = 0; i < length; ++i) {
			T current = objects[i];
			if (i > 0) {
				subClone[i - 1] = objects[0];
			}
			final List<T[]> subPermutations = getPermutations(subClone);
			for (T[] sc : subPermutations) {
				T[] clone = Arrays.copyOf(objects, length);
				clone[0] = current;
				System.arraycopy(sc, 0, clone, 1, length - 1);
				result.add(clone);
			}
			if (i > 0) {
				subClone[i - 1] = current;
			}
		}
		return result;
	}
}
