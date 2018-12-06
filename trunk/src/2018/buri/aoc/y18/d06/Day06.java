package buri.aoc.y18.d06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day06 extends Puzzle {

	/**
	 * Input: A coordinate pair on each line
	 * Output: A list of positions
	 */
	public static List<Position> getInput(int fileIndex) {
		List<Position> data = new ArrayList<>();
		for (String rawString : readFile("2018/06", fileIndex)) {
			data.add(new Position(rawString));
		}
		return (data);
	}

	/**
	 * Using only the Manhattan distance, determine the area around each coordinate by counting the number of integer
	 * X,Y locations that are closest to that coordinate (and aren't tied in distance to any other coordinate).
	 * 
	 * What is the size of the largest area that isn't infinite?
	 */
	public static int getPart1Result(List<Position> input) {
		int gridLength = getGridLength(input);
		Set<Position> outerPositions = new HashSet<>();

		// Calculate the total number of spots with a minimum MD to each location in the input.
		Map<Position, Integer> minimumDistances = new HashMap<>();
		for (int x = 0; x < gridLength; x++) {
			for (int y = 0; y < gridLength; y++) {
				Position currentSpot = new Position(x, y);

				// Calculate all MDs between input locations and this current spot.
				Map<Position, Integer> currentSpotDistances = new HashMap<>();
				for (Position position : input) {
					currentSpotDistances.put(position, getMDBetween(currentSpot, position));
				}

				// Get the smallest MD to record in the grid.
				Map.Entry<Position, Integer> minEntry = null;
				for (Map.Entry<Position, Integer> entry : currentSpotDistances.entrySet()) {
					if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
						minEntry = entry;
					}
				}
				// Ignore any smallest MDs shared by multiple locations.
				if (Collections.frequency(currentSpotDistances.values(), minEntry.getValue()) == 1) {
					Position nearestPosition = minEntry.getKey();
					// Keep track of locations nearest to the edges of the grid, so they can be ignored later.
					if (currentSpot.getX() == 0 || currentSpot.getY() == 0 || currentSpot.getX() == gridLength - 1
						|| currentSpot.getY() == gridLength - 1) {
						outerPositions.add(nearestPosition);
					}

					// Increment the number of minimumMDs owned by an input location.
					if (minimumDistances.get(nearestPosition) == null) {
						minimumDistances.put(nearestPosition, 0);
					}
					minimumDistances.put(nearestPosition, minimumDistances.get(nearestPosition) + 1);
				}
			}
		}

		// Zero out the records for locations along the edges.
		for (Position position : outerPositions) {
			minimumDistances.put(position, 0);
		}

		// Finally, return the max number of minimumMDs owned by an input location.
		Map.Entry<Position, Integer> maxEntry = null;
		for (Map.Entry<Position, Integer> entry : minimumDistances.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return (maxEntry.getValue());
	}

	/**
	 * On the other hand, if the coordinates are safe, maybe the best you can do is try to find a region near as many
	 * coordinates as possible.
	 * 
	 * For example, suppose you want the sum of the Manhattan distance to all of the coordinates to be less than 32. For
	 * each location, add up the distances to all of the given coordinates; if the total of those distances is less than
	 * 32, that location is within the desired region.
	 */
	public static int getPart2Result(int distanceLimit, List<Position> input) {
		int gridLength = getGridLength(input);

		int regionSize = 0;
		for (int x = 0; x < gridLength; x++) {
			for (int y = 0; y < gridLength; y++) {
				Position currentSpot = new Position(x, y);
				// Calculate the sum of all MDs to the current spot.
				int mdSum = 0;
				for (Position position : input) {
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
	private static int getMDBetween(Position p1, Position p2) {
		return (Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()));
	}

	/**
	 * Calculates a suitable square grid size, based on largest position coordinate.
	 */
	private static int getGridLength(List<Position> input) {
		int gridLength = 0;
		for (Position position : input) {
			gridLength = Math.max(gridLength, position.getX());
			gridLength = Math.max(gridLength, position.getY());
		}
		return (gridLength + 1);
	}
}