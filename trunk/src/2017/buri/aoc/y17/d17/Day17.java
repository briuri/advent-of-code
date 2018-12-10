package buri.aoc.y17.d17;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * This spinlock's algorithm is simple but efficient. It starts with a circular buffer containing only the value 0,
 * which it marks as the current position. It then steps forward through the circular buffer some number of steps (your
 * puzzle input) before inserting the first new value, 1, after the value it stopped on. The inserted value becomes the
 * current position. Then, it steps forward from there the same number of steps, and wherever it stops, inserts after it
 * the second new value, 2, and uses that as the new current position again.
 * 
 * It repeats this process of stepping forward, inserting a new value, and using the location of the inserted value as
 * the new current position a total of 2017 times, inserting 2017 as its final operation, and ending with a total of
 * 2018 values (including 0) in the circular buffer.
 * 
 * @author Brian Uri!
 */
public class Day17 extends Puzzle {

	/**
	 * Part 1:
	 * What is the value after 2017 in your completed circular buffer?
	 * 
	 * Part 2:
	 * What is the value after 0 the moment 50000000 is inserted?
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