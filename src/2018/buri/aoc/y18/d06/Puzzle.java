package buri.aoc.y18.d06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Pair;

/**
 * Day 6: Chronal Coordinates
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the size of the largest area that isn't infinite?
	 *
	 * Part 2:
	 * What is the size of the region containing all locations which have a total distance to all given coordinates of
	 * less than 10000?
	 */
	public static int getResult(Part part, List<String> input, int distanceLimit) {
		List<Pair> data = new ArrayList<>();
		for (String line : input) {
			data.add(new Pair(line, Integer.class));
		}

		if (part == Part.ONE) {
			int gridLength = getGridLength(data);
			Set<Pair> outerPairs = new HashSet<>();

			// Calculate the total number of spots with a minimum MD to each location in the input.
			Map<Pair, Integer> minimumDistances = new HashMap<>();
			for (int x = 0; x < gridLength; x++) {
				for (int y = 0; y < gridLength; y++) {
					Pair<Integer> currentSpot = new Pair(x, y);

					// Calculate all MDs between input locations and this current spot.
					Map<Pair, Long> currentSpotDistances = new HashMap<>();
					for (Pair position : data) {
						currentSpotDistances.put(position, currentSpot.getManhattanDistance(position));
					}

					// Get the smallest MD to record in the grid.
					Map.Entry<Pair, Long> minEntry = getMin(currentSpotDistances);
					// Ignore any smallest MDs shared by multiple locations.
					if (Collections.frequency(currentSpotDistances.values(), minEntry.getValue()) == 1) {
						Pair nearestPair = minEntry.getKey();
						// Keep track of locations nearest to the edges of the grid, so they can be ignored later.
						if (currentSpot.getX() == 0 || currentSpot.getY() == 0 || currentSpot.getX() == gridLength - 1
							|| currentSpot.getY() == gridLength - 1) {
							outerPairs.add(nearestPair);
						}

						// Increment the number of minimumMDs owned by an input location.
						minimumDistances.put(nearestPair, minimumDistances.getOrDefault(nearestPair, 0) + 1);
					}
				}
			}

			// Zero out the records for locations along the edges.
			for (Pair position : outerPairs) {
				minimumDistances.put(position, 0);
			}

			// Finally, return the max number of minimumMDs owned by an input location.
			Map.Entry<Pair, Integer> maxEntry = getMax(minimumDistances);
			return (maxEntry.getValue());
		}

		int gridLength = getGridLength(data);

		int regionSize = 0;
		for (int y = 0; y < gridLength; y++) {
			for (int x = 0; x < gridLength; x++) {

				Pair currentSpot = new Pair(x, y);
				// Calculate the sum of all MDs to the current spot.
				int mdSum = 0;
				for (Pair position : data) {
					mdSum += currentSpot.getManhattanDistance(position);
				}
				// Increment region size if the MD sum is within the limit.
				if (mdSum < distanceLimit) {
					regionSize++;
				}
			}
		}
		return (regionSize);
	}

	/**
	 * Calculates a suitable square grid size, based on largest position coordinate.
	 */
	private static int getGridLength(List<Pair> input) {
		int gridLength = 0;
		for (Pair<Integer> position : input) {
			gridLength = Math.max(gridLength, position.getX());
			gridLength = Math.max(gridLength, position.getY());
		}
		return (gridLength + 1);
	}
}