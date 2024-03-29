package buri.aoc.y20.d23;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 23: Crab Cups
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("67384529", 1, false);
		assertRun("45798623", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("149245887792", 1, false);
		assertRun("235551949822", 0, true);
	}

	/**
	 * Part 1:
	 * Using your labeling, simulate 100 moves. What are the labels on the cups after cup 1?
	 *
	 * Originally, I solved this by repurposing the spinlock from 2017 Day 17. This was too slow in Part 2 so I had to
	 * implement a linked list.
	 *
	 * Part 2:
	 * Determine which two cups will end up immediately clockwise of cup 1. What do you get if you multiply their labels
	 * together?
	 */
	protected String runString(Part part, List<String> input) {
		// Set up the cups.
		Map<Integer, Cup> cups = new HashMap<>();
		Cup first = null;
		Cup left = null;
		for (int i = 0; i < input.get(0).length(); i++) {
			Cup cup = new Cup(Integer.parseInt(String.valueOf(input.get(0).charAt(i))));
			addCup(cups, cup, left);
			left = cup;
			if (first == null) {
				first = cup;
			}
		}
		if (part == Part.TWO) {
			for (int i = input.get(0).length() + 1; i <= 1000000; i++) {
				Cup cup = new Cup(i);
				addCup(cups, cup, left);
				left = cup;
			}
		}
		// Wrap around.
		left.setNext(first);

		int maxValue = cups.size();
		int moves = (part == Part.ONE ? 100 : 10000000);
		Cup current = first;
		for (int i = 0; i < moves; i++) {
			// Pickup first 3 clockwise to right.
			Cup pickup1 = current.getNext();
			Cup pickup2 = pickup1.getNext();
			Cup pickup3 = pickup2.getNext();
			current.setNext(pickup3.getNext());

			// Pick destination starting from current value. Wrap around.
			int destinationValue = (current.getValue() - 1 > 0 ? current.getValue() - 1 : maxValue);
			while (destinationValue == pickup1.getValue() || destinationValue == pickup2.getValue()
				|| destinationValue == pickup3.getValue()) {
				destinationValue = (destinationValue - 1 > 0 ? destinationValue - 1 : maxValue);
			}
			Cup destination = cups.get(destinationValue);
			pickup3.setNext(destination.getNext());
			destination.setNext(pickup1);

			current = current.getNext();
		}
		if (part == Part.ONE) {
			StringBuilder builder = new StringBuilder();
			Cup next = cups.get(1).getNext();
			while (next.getValue() != 1) {
				builder.append(next.getValue());
				next = next.getNext();
			}
			return (builder.toString());
		}
		Cup next = cups.get(1).getNext();
		BigInteger factor1 = PuzzleMath.toBigInt(next.getValue());
		BigInteger factor2 = PuzzleMath.toBigInt(next.getNext().getValue());
		return (factor1.multiply(factor2).toString());
	}

	/**
	 * Initializes a cup with its neighbor.
	 */
	protected static void addCup(Map<Integer, Cup> cups, Cup cup, Cup left) {
		if (left != null) {
			left.setNext(cup);
		}
		cups.put(cup.getValue(), cup);
	}
}