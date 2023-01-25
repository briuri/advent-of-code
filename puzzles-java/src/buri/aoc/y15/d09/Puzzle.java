package buri.aoc.y15.d09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Permutations;

/**
 * Day 9: All in a Single Night
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the distance of the shortest route?
	 *
	 * Part 2:
	 * The next year, just to show off, Santa decides to take the route with the longest distance instead.
	 */
	public static int getResult(Part part, List<String> input) {
		// Load lookup data
		Map<String, Integer> distances = new HashMap<>();
		List<String> cities = new ArrayList<>();
		for (String line : input) {
			String[] tokens = line.split(" ");
			if (!cities.contains(tokens[0])) {
				cities.add(tokens[0]);
			}
			if (!cities.contains(tokens[2])) {
				cities.add(tokens[2]);
			}
			distances.put(tokens[0] + tokens[2], Integer.valueOf(tokens[4]));
			distances.put(tokens[2] + tokens[0], Integer.valueOf(tokens[4]));
		}

		// Get all possible city orders.
		Integer[] cityOrder = new Integer[cities.size()];
		for (int i = 0; i < cityOrder.length; i++) {
			cityOrder[i] = i;
		}

		int shortestRoute = Integer.MAX_VALUE;
		int longestRoute = Integer.MIN_VALUE;
		for (Integer[] order : Permutations.getPermutations(cityOrder)) {
			int route = 0;
			for (int i = 0; i < order.length - 1; i++) {
				String currentCity = cities.get(order[i]);
				String nextCity = cities.get(order[i + 1]);
				route += distances.get(currentCity + nextCity);
			}
			shortestRoute = Math.min(shortestRoute, route);
			longestRoute = Math.max(longestRoute, route);
		}
		return (part == Part.ONE ? shortestRoute : longestRoute);
	}
}