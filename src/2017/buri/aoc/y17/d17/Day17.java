package buri.aoc.y17.d17;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 17: Spinlock
 * 
 * @author Brian Uri!
 */
public class Day17 extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the value after 2017 in your completed circular buffer?
	 * 
	 * Part 2:
	 * What is the value after 0 the moment 50000000 is inserted?
	 * 
	 * TODO: Optimize Part 2 so it doesn't take so long (103 seconds for puzzle input)
	 */
	public static int getResult(Part part, int input) {
		final int iterations = (part == Part.ONE ? 2018 : 50000000);
		Spinlock lock = new Spinlock(iterations, input);
		int rightValue = 0;
		for (int i = 1; i < iterations; i++) {
			rightValue = lock.spinsert(i);
		}
		if (part == Part.ONE) {
			return (rightValue);
		}
		return (lock.getValueAfterZero());
	}
}