package buri.aoc.y17.d05;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 5: A Maze of Twisty Trampolines, All Alike
 * 
 * @author Brian Uri!
 */
public class Day05 extends Puzzle {

	/**
	 * Returns the input file as a list of integer jump instructions.
	 */
	public static List<Integer> getInput(int fileIndex) {
		return (convertStringsToInts(readFile("2017/05", fileIndex)));
	}

	/**
	 * Executes the jump instructions in order (input data is mutable). The goal is to follow the jumps until one leads outside the list.
	 * 
	 * Part 1:
	 * - Start at instruction #0 and get the offset stored there.
	 * - Jump forward that many instructions then increment the offset in instruction #0 by 1.
	 * How many steps does it take to reach the exit?
	 * 
	 * Part 2:
	 * - Start at instruction #0 and get the offset stored there.
	 * - Jump forward that many instructions then increment the offset in instruction #0 by 1 (or decrement by 1 if offset >= 3).
	 * How many steps does it take to reach the exit?
	 */
	public static int getResult(Part part, List<Integer> jumps) {
		int numSteps = 0;
		int currentIndex = 0;
		while (currentIndex >= 0 && currentIndex < jumps.size()) {
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
}
