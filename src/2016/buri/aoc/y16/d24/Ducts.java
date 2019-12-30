package buri.aoc.y16.d24;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.data.Permutations;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.path.Pathfinder;
import buri.aoc.data.path.StepStrategy;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for the air ducts.
 * 
 * @author Brian Uri!
 */
public class Ducts extends CharGrid {

	private Map<Integer, Pair> _destinations;

	private static final char OPEN = '.';

	private final StepStrategy<Pair<Integer>> STEP_STRATEGY = new StepStrategy<Pair<Integer>>() {
		@Override
		public List<Pair<Integer>> getNextSteps(Pair<Integer> current) {
			List<Pair<Integer>> nextSteps = current.getAdjacent();
			for (Iterator<Pair<Integer>> iterator = nextSteps.iterator(); iterator.hasNext();) {
				Pair<Integer> position = iterator.next();
				// Remove out of bounds or walls.
				if (position.getX() < 0 || position.getX() >= getWidth()
					|| position.getY() < 0 || position.getY() >= getHeight()
					|| get(position) != OPEN) {
					iterator.remove();
				}
			}
			return (nextSteps);
		}
	};

	/**
	 * Constructor
	 */
	public Ducts(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		_destinations = new HashMap<>();
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < getWidth(); x++) {
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
			cameFroms.put(start, Pathfinder.explore(start, STEP_STRATEGY));
		}

		// Get all possible path permutations.
		Integer[] order = new Integer[getDestinations().size()];
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		List<Integer[]> permutations = Permutations.getPermutations(order);
		// Remove any that don't start at 0.
		for (Iterator<Integer[]> iterator = permutations.iterator(); iterator.hasNext();) {
			if (iterator.next()[0] != 0) {
				iterator.remove();
			}
		}

		// Compute path for all permutations that start at 0.
		int shortestPath = Integer.MAX_VALUE;
		for (Integer[] permutation : permutations) {
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
	 * Accessor for the destinations
	 */
	private Map<Integer, Pair> getDestinations() {
		return _destinations;
	}
}
