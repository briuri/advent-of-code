package buri.aoc.y22.d12;

import buri.aoc.common.Part;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.path.Path;
import buri.aoc.common.data.path.Pathfinder;
import buri.aoc.common.data.path.StepStrategy;
import buri.aoc.common.data.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Data model for the hill climbing grid.
 *
 * @author Brian Uri!
 */
public class Grid extends CharGrid {
	private Pair<Integer> _originalStart, _end;

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	private final StepStrategy<Pair<Integer>> STEP_STRATEGY = new StepStrategy<Pair<Integer>>() {
		@Override
		public List<Pair<Integer>> getNextSteps(Pair<Integer> current) {
			List<Pair<Integer>> nextSteps = current.getAdjacent();
			// Remove neighbors that are too high.
			nextSteps.removeIf(position -> !isInBounds(position) || getHeight(get(position)) > getHeight(get(current)) + 1);
			return (nextSteps);
		}
	};

	/**
	 * Constructor
	 */
	public Grid(List<String> input) {
		super(new Pair<>(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				char value = input.get(y).charAt(x);
				if (value == 'S') {
					_originalStart = new Pair<>(x, y);
					value = 'a';
				}
				if (value == 'E') {
					_end = new Pair<>(x, y);
					value = 'z';
				}
				set(x, y, value);
			}
		}
	}

	/**
	 * Returns the fewest number of steps needed to reach the signal point.
	 */
	public int getSteps(Part part) {
		List<Pair<Integer>> starts = new ArrayList<>();
		starts.add(getOriginalStart());
		if (part == Part.TWO) {
			for (int y = 0; y < getHeight(); y++) {
				for (int x = 0; x < getWidth(); x++) {
					if (get(x, y) == 'a') {
						starts.add(new Pair<>(x, y));
					}
				}
			}
		}

		List<Integer> steps = new ArrayList<>();
		for (Pair<Integer> start : starts) {
			steps.add(getSteps(start, getEnd()));
		}
		Collections.sort(steps);
		return (steps.get(0));
	}

	/**
	 * Returns the minimum number of steps to a single destination.
	 */
	private int getSteps(Pair<Integer> start, Pair<Integer> destination) {
		Map<Pair<Integer>, Pair<Integer>> cameFrom = Pathfinder.breadthFirstSearch(start, STEP_STRATEGY);
		List<Pair<Integer>> destinations = new ArrayList<>();
		destinations.add(destination);
		List<Path<Pair<Integer>>> paths = Pathfinder.toPaths(destinations, cameFrom);
		// Some starts are blocked off from the end.
		if (paths.isEmpty()) {
			return (Integer.MAX_VALUE);
		}
		// There will be 1 path returned, since only 1 destination was provided.
		return (paths.get(0).getLength());
	}

	/**
	 * Returns the height of each grid position.
	 */
	private int getHeight(char value) {
		return (ALPHABET.indexOf(value));
	}

	/**
	 * Accessor for the original starting point.
	 */
	private Pair<Integer> getOriginalStart() {
		return _originalStart;
	}

	/**
	 * Accessor for the signal point.
	 */
	private Pair<Integer> getEnd() {
		return _end;
	}
}