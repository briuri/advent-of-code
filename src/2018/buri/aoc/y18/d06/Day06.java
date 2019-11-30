package buri.aoc.y18.d06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.Puzzle;
import buri.aoc.data.Pair;

/**
 * Day 6: Chronal Coordinates
 * 
 * @author Brian Uri!
 */
public class Day06 extends Puzzle {

	/**
	 * Returns the input file as a list of coordinate pairs.
	 */
	public static List<Pair> getInput(int fileIndex) {
		List<Pair> data = new ArrayList<>();
		for (String rawString : readFile("2018/06", fileIndex)) {
			data.add(new Pair(rawString));
		}
		return (data);
	}

	/**
	 * Part 1:
	 * What is the size of the largest area that isn't infinite?
	 */
	public static int getPart1Result(List<Pair> input) {
		int gridLength = getGridLength(input);
		Set<Pair> outerPairs = new HashSet<>();

		// Calculate the total number of spots with a minimum MD to each location in the input.
		Map<Pair, Integer> minimumDistances = new HashMap<>();
		for (int x = 0; x < gridLength; x++) {
			for (int y = 0; y < gridLength; y++) {
				Pair currentSpot = new Pair(x, y);

				// Calculate all MDs between input locations and this current spot.
				Map<Pair, Integer> currentSpotDistances = new HashMap<>();
				for (Pair position : input) {
					currentSpotDistances.put(position, getMDBetween(currentSpot, position));
				}

				// Get the smallest MD to record in the grid.
				Map.Entry<Pair, Integer> minEntry = getMin(currentSpotDistances);
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

	/**
	 * Part 2:
	 * What is the size of the region containing all locations which have a total distance to all given coordinates of
	 * less than 10000?
	 */
	public static int getPart2Result(int distanceLimit, List<Pair> input) {
		int gridLength = getGridLength(input);

		int regionSize = 0;
		for (int y = 0; y < gridLength; y++) {
			for (int x = 0; x < gridLength; x++) {

				Pair currentSpot = new Pair(x, y);
				// Calculate the sum of all MDs to the current spot.
				int mdSum = 0;
				for (Pair position : input) {
					mdSum += getMDBetween(currentSpot, position);
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
	 * Calculates the Manhattan distance between two positions.
	 */
	private static int getMDBetween(Pair p1, Pair p2) {
		return (Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()));
	}

	/**
	 * Calculates a suitable square grid size, based on largest position coordinate.
	 */
	private static int getGridLength(List<Pair> input) {
		int gridLength = 0;
		for (Pair position : input) {
			gridLength = Math.max(gridLength, position.getX());
			gridLength = Math.max(gridLength, position.getY());
		}
		return (gridLength + 1);
	}
}