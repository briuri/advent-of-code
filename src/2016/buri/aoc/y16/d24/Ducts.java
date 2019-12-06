package buri.aoc.y16.d24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import buri.aoc.Part;
import buri.aoc.data.Pair;
import buri.aoc.data.Permutations;
import buri.aoc.data.grid.CharGrid;

/**
 * Data model for the air ducts.
 * 
 * @author Brian Uri!
 */
public class Ducts extends CharGrid {

	private Map<Integer, Pair> _destinations;

	private static final char OPEN = '.';

	/**
	 * Constructor
	 */
	public Ducts(List<String> input) {
		super(input.get(0).length());
		_destinations = new HashMap<>();
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < getSize(); x++) {
				char value = line.charAt(x);
				if (value >= '0' && value <= '9') {
					set(x, y, OPEN);
					getDestinations().put(Character.getNumericValue(value), new Pair(x, y));
				}
				else {
					set(x, y, value);
				}
			}
		}
	}

	/**
	 * Returns the fewest steps needed to visit all numbered spots.
	 */
	public int getFewestSteps(Part part) {
		// Do all the traversal for path-building upfront.
		Map<Pair, Map<Pair, Pair>> cameFroms = new HashMap<>();
		for (Pair start : getDestinations().values()) {
			cameFroms.put(start, getCameFrom(start));
		}

		// Get all possible path permutations.
		int[] order = new int[getDestinations().size()];
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		List<int[]> permutations = Permutations.getPermutations(order);
		// Remove any that don't start at 0.
		for (Iterator<int[]> iterator = permutations.iterator(); iterator.hasNext();) {
			if (iterator.next()[0] != 0) {
				iterator.remove();
			}
		}

		// Compute path for all permutations that start at 0.
		int shortestPath = Integer.MAX_VALUE;
		for (int[] permutation : permutations) {
			int path = 0;
			for (int i = 0; i < permutation.length; i++) {
				Pair current = getDestinations().get(permutation[i]);
				if (part == Part.ONE && i == permutation.length - 1) {
					continue;
				}
				// Add one more step in Part TWO is return to start.
				int nextIndex = (part == Part.TWO && i == permutation.length - 1) ? 0 : permutation[i + 1];
				Pair next = getDestinations().get(nextIndex);
				path += getStepsBetween(current, next, cameFroms.get(current));
			}
			shortestPath = Math.min(shortestPath, path);
		}
		return (shortestPath);
	}

	/**
	 * Do a BFS from one position to all other positions.
	 */
	private Map<Pair, Pair> getCameFrom(Pair start) {
		Queue<Pair> frontier = new ArrayDeque<>();
		frontier.add(start);
		Map<Pair, Pair> cameFrom = new HashMap<>();
		cameFrom.put(start, null);
		Pair current = null;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			for (Pair next : getTraversableNeighbors(current)) {
				// Don't return to starting point.
				if (next.equals(start)) {
					continue;
				}
				if (cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}
		return (cameFrom);
	}

	/**
	 * Returns the number of steps between two positions.
	 */
	private int getStepsBetween(Pair start, Pair destination, Map<Pair, Pair> cameFrom) {
		int steps = 0;
		if (!destination.equals(start)) {
			Pair previous = cameFrom.get(destination);
			while (previous != null) {
				steps++;
				previous = cameFrom.get(previous);
			}
		}
		return (steps);
	}

	/**
	 * Returns open cells adjacent to some position.
	 */
	private List<Pair> getTraversableNeighbors(Pair center) {
		List<Pair> neighbors = new ArrayList<>();
		if (center.getY() < getSize() - 1) {
			neighbors.add(new Pair(center.getX(), center.getY() + 1));
		}
		if (center.getY() > 0) {
			neighbors.add(new Pair(center.getX(), center.getY() - 1));
		}
		if (center.getX() > 0) {
			neighbors.add(new Pair(center.getX() - 1, center.getY()));
		}
		if (center.getX() < getSize() - 1) {
			neighbors.add(new Pair(center.getX() + 1, center.getY()));
		}

		// Remove any that are walls.
		for (Iterator<Pair> iterator = neighbors.iterator(); iterator.hasNext();) {
			Pair position = iterator.next();
			if (get(position) != OPEN) {
				iterator.remove();
			}
		}
		return (neighbors);
	}

	/**
	 * Accessor for the destinations
	 */
	private Map<Integer, Pair> getDestinations() {
		return _destinations;
	}
}