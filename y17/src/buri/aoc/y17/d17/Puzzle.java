package buri.aoc.y17.d17;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 17: Spinlock
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the value after 2017 in your completed circular buffer?
	 * 
	 * Part 2:
	 * What is the value after 0 the moment 50000000 is inserted?
	 */
	public static int getResult(Part part, int input) {
		final int iterations = (part == Part.ONE) ? 2018 : 50000000;
		if (part == Part.ONE) {
			Spinlock lock = new Spinlock(iterations, input);
			int rightValue = 0;
			for (int i = 1; i < iterations; i++) {
				rightValue = lock.spinsert(i);
			}
			return (rightValue);
		}

		// Part TWO
		int currentPosition = 0;
		int result = 0;
		int i = 0;
		while (i < iterations) {
			if (currentPosition == 1) {
				result = i;
			}
			int fits = (i - currentPosition) / input;
			i += (fits + 1);
			currentPosition = (currentPosition + (fits + 1) * (input + 1) - 1) % i + 1;
		}
		return (result);
	}
}