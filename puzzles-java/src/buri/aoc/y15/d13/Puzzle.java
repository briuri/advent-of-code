package buri.aoc.y15.d13;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Permutations;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 13: Knights of the Dinner Table
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(330L, 1, false);
		assertRun(709L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(668L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the total change in happiness for the optimal seating arrangement of the actual guest list?
	 *
	 * Part 2:
	 * What is the total change in happiness for the optimal seating arrangement that actually includes yourself?
	 */
	protected long runLong(Part part, List<String> input) {
		List<String> guests = new ArrayList<>();
		Map<String, Integer> happiness = new HashMap<>();
		for (String line : input) {
			String[] tokens = line.split(" ");
			String currentGuest = tokens[0];
			String nextGuest = tokens[10].substring(0, tokens[10].length() - 1);
			int value = Integer.parseInt(tokens[3]);
			if (tokens[2].equals("lose")) {
				value *= -1;
			}
			happiness.put(currentGuest + nextGuest, value);
			if (!guests.contains(currentGuest)) {
				guests.add(currentGuest);
			}
		}
		if (part == Part.TWO) {
			for (String guest : guests) {
				happiness.put("Me" + guest, 0);
				happiness.put(guest + "Me", 0);
			}
			guests.add("Me");
		}

		// Get all possible guest orders.
		Integer[] guestOrder = new Integer[guests.size()];
		for (int i = 0; i < guestOrder.length; i++) {
			guestOrder[i] = i;
		}

		int bestHappiness = Integer.MIN_VALUE;
		for (Integer[] order : Permutations.getPermutations(guestOrder)) {
			int currentHappiness = 0;
			for (int i = 0; i < order.length; i++) {
				String currentGuest = guests.get(order[i]);
				String nextGuest = guests.get(order[(i + 1) % order.length]);
				currentHappiness += happiness.get(currentGuest + nextGuest);
				currentHappiness += happiness.get(nextGuest + currentGuest);
			}
			bestHappiness = Math.max(bestHappiness, currentHappiness);
		}
		return (bestHappiness);
	}
}