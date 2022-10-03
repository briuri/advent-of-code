package buri.aoc.y21.d15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Pair;

/**
 * Day 15: Chiton
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the lowest total risk of any path from the top left to the bottom right?
	 *
	 * Part 2:
	 * Using the full map, what is the lowest total risk of any path from the top left to the bottom right?
	 */
	public static long getResult(Part part, List<String> input) {
		RiskMap map = RiskMap.buildMap(part, input);

		// Tracks the lowest risk upon arrival at a point so we don't bother with more expensive paths.
		Map<Pair, Integer> lowestRisksSoFar = new HashMap<>();

		// Tracks the adjacent points to explore next
		List<PointRisk> frontier = new ArrayList<>();
		frontier.add(new PointRisk(new Pair<Integer>(0, 0), 0));

		while (true) {
			// Evaluate in ascending order of risk so far to search map in order of most promising entries.
			Collections.sort(frontier);
			PointRisk current = frontier.remove(0);

			// Then, first time arriving at lower right corner should be the one with lowest risk possible.
			if (current.getPoint().equals(map.getEnd())) {
				return (current.getRiskSoFar());
			}

			// Determine how much risk will increase when visiting adjacent points and queue them up for evaluation.
			for (Pair<Integer> adj : current.getPoint().getAdjacent()) {
				if (map.isInBounds(adj)) {
					int newRisk = current.getRiskSoFar() + map.get(adj);
					// Only add points where accumulated risk is lower than previous arrivals to that spot.
					// No need to track previously visited squares, because going BACK will always be a higher newRisk.
					if (newRisk < lowestRisksSoFar.getOrDefault(adj, Integer.MAX_VALUE)) {
						lowestRisksSoFar.put(adj, newRisk);
						frontier.add(new PointRisk(adj, newRisk));
					}
				}
			}
		}
	}
}