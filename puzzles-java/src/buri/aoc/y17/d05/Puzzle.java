package buri.aoc.y17.d05;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 5: A Maze of Twisty Trampolines, All Alike
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(5L, 1, false);
		assertRun(336905L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(10L, 1, false);
		assertRun(21985262L, 0, true);
	}

	/**
	 * Executes the jump instructions in order (input data is mutable). The goal is to follow the jumps until one leads
	 * outside the list.
	 *
	 * Part 1:
	 * - Start at instruction #0 and get the offset stored there.
	 * - Jump forward that many instructions then increment the offset in instruction #0 by 1.
	 * How many steps does it take to reach the exit?
	 *
	 * Part 2:
	 * - Start at instruction #0 and get the offset stored there.
	 * - Jump forward that many instructions then increment the offset in instruction #0 by 1 (or decrement by 1 if
	 * offset >= 3).
	 * How many steps does it take to reach the exit?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> jumps = convertStringsToInts(input);

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