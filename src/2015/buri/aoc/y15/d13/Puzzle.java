package buri.aoc.y15.d13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Permutations;

/**
 * Day 13: Knights of the Dinner Table
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * What is the total change in happiness for the optimal seating arrangement of the actual guest list?
	 * 
	 * Part 2:
	 * What is the total change in happiness for the optimal seating arrangement that actually includes yourself?
	 */
	public static int getResult(Part part, List<String> input) {
		List<String> guests = new ArrayList<>();
		Map<String, Integer> happiness = new HashMap<>();
		for (String line : input) {
			String[] tokens = line.split(" ");
			String currentGuest = tokens[0];
			String nextGuest = tokens[10].substring(0, tokens[10].length() - 1);
			int value = Integer.valueOf(tokens[3]);
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