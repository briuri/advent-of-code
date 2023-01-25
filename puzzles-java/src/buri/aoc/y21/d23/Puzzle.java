package buri.aoc.y21.d23;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import buri.aoc.common.BasePuzzle;

/**
 * Day 23: Amphipod
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * I originally did this puzzle by hand in Google Sheets. I coded up this solution over the next few days.
	 *
	 * Input files are modified in the following ways:
	 * - # walls are added so each line is equal length and part 1/2 have the same height.
	 * - Any letters already in a completed position are replaced with a #.
	 *
	 * Part 1:
	 * What is the least energy required to organize the amphipods?
	 *
	 * Part 2:
	 * Using the initial configuration from the full diagram, what is the least energy required to organize the
	 * amphipods?
	 */
	public static long getResult(List<String> input) {
		Map<State, Long> lowestCosts = new HashMap<>();
		long lowestEnd = Long.MAX_VALUE;

		// Tracks the next moves to consider
		Stack<State> frontier = new Stack<>();
		frontier.push(new State(input));
		while (!frontier.isEmpty()) {
			for (State next : frontier.remove(0).getNextStates()) {
				if (next.getCost() < lowestCosts.getOrDefault(next, Long.MAX_VALUE)) {
					if (next.isEnd()) {
						lowestEnd = Math.min(lowestEnd, next.getCost());
					}
					// Don't keep exploring if it's obvious we will exceed the lowest end cost.
					else if (next.getCost() < lowestEnd) {
						frontier.push(next);
					}
					lowestCosts.put(next, next.getCost());
				}
			}
		}
		return (lowestEnd);
	}
}