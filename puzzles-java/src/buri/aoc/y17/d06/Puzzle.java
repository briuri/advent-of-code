package buri.aoc.y17.d06;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 6: Memory Reallocation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many redistribution cycles must be completed before a configuration is produced that has been seen before?
	 *
	 * Part 2:
	 * How many cycles are in the infinite loop that arises from the configuration in your puzzle input?
	 */
	public static int getResult(Part part, List<String> input) {
		String[] rawIntegers = input.get(0).split("\t");
		List<Integer> banks = convertStringsToInts(Arrays.asList(rawIntegers));

		boolean hitLoop = false;
		int attempts = 0;
		Set<String> snapshots = new HashSet<>();
		MemoryBanks memory = new MemoryBanks(banks);
		while (true) {
			String snapshot = memory.redistribute();
			attempts++;
			if (snapshots.contains(snapshot)) {
				if (part == Part.ONE || (part == Part.TWO && hitLoop)) {
					return (attempts);
				}
				attempts = 0;
				hitLoop = true;
				snapshots.clear();
			}
			snapshots.add(snapshot);
		}
	}
}