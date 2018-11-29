package buri.aoc.y17.d05;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The message includes a list of the offsets for each jump. Jumps are relative: -1 moves to the previous instruction,
 * and 2 skips the next one. Start at the first instruction in the list. The goal is to follow the jumps until one leads
 * outside the list.
 * 
 * Part 1:
 * In addition, these instructions are a little strange; after each jump, the offset of that instruction increases by 1.
 * So, if you come across an offset of 3, you would move three instructions forward, but change it to a 4 for the next
 * time it is encountered.
 * 
 * Part 2:
 * Now, the jumps are even stranger: after each jump, if the offset was three or more, instead decrease it by 1.
 * Otherwise, increase it by 1 as before.
 * 
 * @author Brian Uri!
 */
public class Day05 extends Puzzle {
	
	/**
	 * Input: One number per line.
	 * Output: List of numbers.
	 */
	public static List<Integer> getInput(int fileIndex) {
		return (convertStringsToInts(readFile("2017/05", fileIndex)));
	}
	
	/**
	 * Executes the jump instructions in order (input data is mutable).
	 */
	public static int getSteps(Part part, List<Integer> jumps) {
		assertValidJumps(jumps);
		final int listSize = jumps.size();
		int numSteps = 0;
		int currentIndex = 0;
		while (currentIndex >= 0 && currentIndex < listSize) {
			Integer nextInstruction = jumps.get(currentIndex);

			// Modify current instruction before jumping.
			int increment = 1;
			if (part == Part.TWO && nextInstruction >= 3) {
				increment = -1;
			}
			jumps.set(currentIndex, nextInstruction + increment);

			// Now execute jump.
			currentIndex += nextInstruction;
			numSteps++;
		}
		return (numSteps);
	}
	
	/**
	 * Validates input, which must be non-null and non-empty.
	 */
	private static void assertValidJumps(List<Integer> jumps) {
		if (jumps == null || jumps.isEmpty()) {
			throw new IllegalArgumentException("Jump list must be non-null and non-empty.");
		}
	}
}
