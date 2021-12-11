package buri.aoc.y20.d13;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 13: Shuttle Search
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the ID of the earliest bus you can take to the airport multiplied by the number of minutes you'll need to
	 * wait for that bus?
	 *
	 * Part 2:
	 * What is the earliest timestamp such that all of the listed bus IDs depart at offsets matching their positions in
	 * the list?
	 */
	public static long getResult(Part part, List<String> input) {
		String[] tokens = input.get(1).split(",");
		Map<Integer, Integer> indexesToIds = new HashMap<>();
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].equals("x")) {
				indexesToIds.put(i, Integer.valueOf(tokens[i]));
			}
		}

		if (part == Part.ONE) {
			int earliest = Integer.valueOf(input.get(0));
			Map<Integer, Integer> earliestBusTimes = new HashMap<>();
			for (Integer bus : indexesToIds.values()) {
				int next = bus * (earliest / bus + 1);
				earliestBusTimes.put(bus, next);
			}
			Map.Entry<Integer, Integer> min = getMin(earliestBusTimes);
			return (min.getKey() * (min.getValue() - earliest));
		}

		// Naive approach
		/*
		long increment = 1L;
		while (true) {
			long t = indexesToIds.get(0) * increment;
			boolean found = true;
			for (Map.Entry<Integer, Integer> entry : indexesToIds.entrySet()) {
				if ((t + entry.getKey()) % entry.getValue() != 0) {
					increment++;
					found = false;
					break;
				}
			}
			if (found) {
				return (t);
			}
		}
		*/

		long increment = indexesToIds.get(0);
		long timestamp = 0L;
		// Solve 2 buses at a time and use LCM to solve for "all previous buses + next bus" instead of "every bus".
		for (Map.Entry<Integer, Integer> entry : indexesToIds.entrySet()) {
			if (entry.getKey() != 0) {
				while ((timestamp + entry.getKey()) % entry.getValue() != 0) {
					timestamp += increment;
				}
				increment = increment * entry.getValue();
			}
		}
		return (timestamp);
	}
}