package buri.aoc.y17.d17;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 17: Spinlock
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(638L, 1, false);
		assertRun(1506L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(39479736L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the value after 2017 in your completed circular buffer?
	 *
	 * Part 2:
	 * What is the value after 0 the moment 50000000 is inserted?
	 */
	protected long runLong(Part part, List<String> input) {
		int value = Integer.parseInt(input.get(0));
		final int iterations = (part == Part.ONE) ? 2018 : 50000000;
		if (part == Part.ONE) {
			Spinlock lock = new Spinlock(iterations, value);
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
			int fits = (i - currentPosition) / value;
			i += (fits + 1);
			currentPosition = (currentPosition + (fits + 1) * (value + 1) - 1) % i + 1;
		}
		return (result);
	}
}