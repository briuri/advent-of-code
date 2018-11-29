package buri.aoc.y17.d06;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * A debugger program here is having an issue: it is trying to repair a memory reallocation routine, but it keeps
 * getting stuck in an infinite loop.
 * 
 * In this area, there are sixteen memory banks; each memory bank can hold any number of blocks. The goal of the
 * reallocation routine is to balance the blocks between the memory banks.
 * 
 * The reallocation routine operates in cycles. In each cycle, it finds the memory bank with the most blocks (ties won
 * by the lowest-numbered memory bank) and redistributes those blocks among the banks. To do this, it removes all of the
 * blocks from the selected bank, then moves to the next (by index) memory bank and inserts one of the blocks. It
 * continues doing this until it runs out of blocks; if it reaches the last memory bank, it wraps around to the first
 * one.
 * 
 * The debugger would like to know how many redistributions can be done before a blocks-in-banks configuration is
 * produced that has been seen before.
 * 
 * @author Brian Uri!
 */
public class Day06 extends Puzzle {
	
	/**
	 * Input: One row of tab-delimited numbers.
	 * Output: List of numbers.
	 */
	public static List<Integer> getInput(int fileIndex) {
		String[] rawIntegers = getFileAsString("2017/06", fileIndex).split("\t");
		return (getStringsAsIntegers(Arrays.asList(rawIntegers)));
	}
	
	/**
	 * Find out how many redistribute actions occur before an infinite loop.
	 */
	public static int getIterations(Part part, List<Integer> banks) {
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
