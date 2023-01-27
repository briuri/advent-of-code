package buri.aoc.y20.d10;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 10: Adapter Array
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(35L, 1, false);
		assertRun(220L, 2, false);
		assertRun(1890L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(8L, 1, false);
		assertRun(19208L, 2, false);
		assertRun(49607173328384L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?
	 *
	 * Part 2:
	 * What is the total number of distinct ways you can arrange the adapters to connect the charging outlet to your
	 * device?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> adapters = PuzzleMath.toInts(input);
		Collections.sort(adapters);

		int charge = 0;
		int oneJolt = 0;
		int threeJolts = 0;
		for (Integer adapter : adapters) {
			if (adapter - charge == 1) {
				oneJolt++;
			}
			else {
				threeJolts++;
			}
			charge = adapter;
		}
		// Final voltage is 3 higher than the last adapter.
		threeJolts++;
		if (part == Part.ONE) {
			return ((long) oneJolt * threeJolts);
		}

		int max = oneJolt + 3 * threeJolts;
		return (countPaths(adapters, 0, max, new HashMap<>()));
	}

	/**
	 * Recursively counts the number of ways to reach maximum joltage from a starting joltage. Retains previously
	 * followed paths to avoid re-walking them.
	 */
	protected static long countPaths(List<Integer> adapters, int start, int max, Map<String, Long> visited) {
		// Avoid recalculating if we've already been here.
		String key = adapters.size() + "/" + start;
		if (visited.containsKey(key)) {
			return (visited.get(key));
		}

		long paths = 0;
		// Within 3 jolts of the target joltage without using an adapter, so 1 valid path.
		if (max - start <= 3) {
			paths++;
		}
		// No adapters left
		if (adapters.isEmpty()) {
			return (paths);
		}

		List<Integer> remainingAdapters = adapters.subList(1, adapters.size());
		// This adapter is valid. Use it then recursively check next ones.
		if (adapters.get(0) - start <= 3) {
			paths += countPaths(remainingAdapters, adapters.get(0), max, visited);
		}
		// Recursively check next adapters without using this one.
		paths += countPaths(remainingAdapters, start, max, visited);

		// Save this calculation for next time.
		visited.put(key, paths);
		return (paths);
	}
}