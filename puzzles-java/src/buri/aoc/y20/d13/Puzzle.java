package buri.aoc.y20.d13;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 13: Shuttle Search
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(295, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2845L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1068781L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(754018L, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
		assertEquals(779210L, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(1261476L, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(1202161486L, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(487905974205117L, result);
	}

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