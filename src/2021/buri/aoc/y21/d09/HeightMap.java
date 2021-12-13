package buri.aoc.y21.d09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for the heightmap.
 *
 * @author Brian Uri!
 */
public class HeightMap extends IntGrid {
	private List<Pair<Integer>> _lowestPoints = new ArrayList<>();
	private int _risk = 0;

	private static final int HIGHEST = 9;

	/**
	 * Constructor
	 */
	public HeightMap(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, Character.getNumericValue(input.get(y).charAt(x)));
			}
		}

		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Pair<Integer> point = new Pair(x, y);
				int testValue = get(point);
				boolean isLowest = true;
				for (Pair<Integer> adj : point.getAdjacent()) {
					if (isInBounds(adj)) {
						isLowest = isLowest && (testValue < get(adj));
					}
				}
				if (isLowest) {
					getLowestPoints().add(point);
					_risk += testValue + 1;
				}
			}
		}
	}

	/**
	 * Returns the product of the 3 largest basins centered around the lowest points.
	 */
	public long getBasinProduct() {
		List<Integer> basinSizes = new ArrayList<>();
		for (Pair<Integer> low : getLowestPoints()) {
			Set<Pair<Integer>> visited = new HashSet<>();
			explore(low, visited);
			basinSizes.add(visited.size());
		}
		Collections.sort(basinSizes);
		Collections.reverse(basinSizes);
		return (basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));
	}

	/**
	 * Visits all squares around a point, stopping when an edge or a 9 is hit.
	 */
	private void explore(Pair<Integer> point, Set<Pair<Integer>> visited) {
		if (visited.contains(point)) {
			return;
		}
		visited.add(point);
		for (Pair<Integer> adj : point.getAdjacent()) {
			if (isInBounds(adj) && get(adj) != HIGHEST) {
				explore(adj, visited);
			}
		}
	}

	/**
	 * Accessor for the list of lowest points
	 */
	private List<Pair<Integer>> getLowestPoints() {
		return (_lowestPoints);
	}

	/**
	 * Accessor for the risk level of the lowest points
	 */
	public int getRisk() {
		return (_risk);
	}
}
