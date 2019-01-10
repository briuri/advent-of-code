package buri.aoc.y15.d09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.Permutations;

/**
 * Day 9: All in a Single Night
 * 
 * @author Brian Uri!
 */
public class Day09 extends Puzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2015/09", fileIndex));
	}

	/**
	 * Part 1:
	 * What is the distance of the shortest route?
	 * 
	 * Part 2:
	 * QUESTION
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
		int[] cityOrder = new int[cities.size()];
		for (int i = 0; i < cityOrder.length; i++) {
			cityOrder[i] = i;
		}

		int shortestRoute = Integer.MAX_VALUE;
		int longestRoute = Integer.MIN_VALUE;
		for (int[] order : Permutations.getPermutations(cityOrder)) {
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