package buri.aoc.y22.d20;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Pair;

import java.util.ArrayDeque;
import java.util.List;

/**
 * Day 20: Grove Positioning System
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the sum of the three numbers that form the grove coordinates?
	 *
	 * Part 2:
	 * What is the sum of the three numbers that form the grove coordinates?
	 */
	public static long getResult(Part part, List<String> input) {
		final long DECRYPTION_KEY = (part == Part.ONE ? 1 : 811589153);
		final int NUM_MIXES = (part == Part.ONE ? 1 : 10);

		// Store the data in a deque and include original index number.
		ArrayDeque<Pair<Long>> list = new ArrayDeque<>();
		List<Long> values = convertStringsToLongs(input);
		Pair<Long> zero = null;
		for (int i = 0; i < values.size(); i++) {
			values.set(i, values.get(i) * DECRYPTION_KEY);
			Pair<Long> indexedValue = new Pair<>((long) i, values.get(i));
			list.add(indexedValue);
			if (indexedValue.getY() == 0L) {
				zero = indexedValue;
			}
		}

		// Mix the numbers.
		for (int times = 0; times < NUM_MIXES; times++) {
			for (int i = 0; i < values.size(); i++) {
				mix(list, new Pair<>((long) i, values.get(i)));
			}
		}

		// Rotate so 0 is at the beginning.
		locate(list, zero);
		long sum = 0;
		for (int i = 0; i < 3; i++) {
			rotate(list, 1000);
			sum += list.peek().getY();
		}
		return (sum);
	}

	/**
	 * Cycles the circular list a specific number of spots.
	 */
	protected static void rotate(ArrayDeque<Pair<Long>> list, long times) {
		// Bound the # of times for part 2.
		times = times % list.size();
		if (times >= 0) {
			for (int i = 0; i < times; i++) {
				list.addLast(list.removeFirst());
			}
		}
		else {
			for (int i = 0; i < Math.abs(times); i++) {
				list.addFirst(list.removeLast());
			}
		}
	}

	/**
	 * Cycles the circular list until a specific value is in the 0th position.
	 */
	protected static void locate(ArrayDeque<Pair<Long>> list, Pair<Long> value) {
		while (!list.peek().equals(value)) {
			list.addLast(list.removeFirst());
		}
	}

	/**
	 * Moves a value in the list that many spaces away. Uses a pair to represent the value
	 * because there are duplicates in the real data.
	 */
	protected static void mix(ArrayDeque<Pair<Long>> list, Pair<Long> value) {
		locate(list, value);
		list.removeFirst();
		rotate(list, value.getY());
		list.addFirst(value);
	}
}