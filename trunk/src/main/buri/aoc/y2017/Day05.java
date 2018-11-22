package buri.aoc.y2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
public class Day05 {

	public enum Strategy {
		/* Part 1: Increment by 1 after each jump. */
		ALWAYS_INCREMENT,
		/* Part 2: Decrement by 1 if greater than 3. Increment by 1 otherwise. */
		CONDITIONAL_INCREMENT
	}
	
	/**
	 * Loads the file at the provided path and returns its contents as a List of Integers.
	 * 
	 * @throws IllegalArgumentException on file I/O issues
	 */
	public static List<Integer> getJumpsFromFile(String filePath) {
		List<Integer> jumps = new ArrayList<>();
		try {
			for (String jump : Files.readAllLines(Paths.get(filePath))) {
				try {
					jumps.add(Integer.valueOf(jump));
				}
				catch (NumberFormatException e) {
					throw new IllegalArgumentException(e.getMessage(), e);
				}
			}
			return (jumps);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}

	/**
	 * Private to avoid instantiation.
	 */
	private Day05() {}

	/**
	 * Executes the jump instructions in order (input data is mutable).
	 * 
	 * @param strategy the strategy for following the instructions
	 * @param jumps the list of jump instructions
	 * @return the number of steps it takes to escape the list.
	 */
	public static int getSteps(Strategy strategy, List<Integer> jumps) {
		assertValidJumps(strategy, jumps);
		final int listSize = jumps.size();
		int numSteps = 0;
		int currentIndex = 0;
		while (currentIndex >= 0 && currentIndex < listSize) {
			Integer nextInstruction = jumps.get(currentIndex);

			// Modify current instruction before jumping.
			int increment = 1;
			if (strategy == Strategy.CONDITIONAL_INCREMENT && nextInstruction >= 3) {
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
	 * 
	 * @param strategy the strategy for following the instructions
	 * @param jumps the list of jump instructions
	 */
	private static void assertValidJumps(Strategy strategy, List<Integer> jumps) {
		if (jumps == null || jumps.isEmpty()) {
			throw new IllegalArgumentException("Jump list must be non-null and non-empty.");
		}
	}
}
