package buri.aoc.y16.d13;

import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.path.Path;
import buri.aoc.common.data.path.Pathfinder;
import buri.aoc.common.data.path.StepStrategy;
import buri.aoc.common.data.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Map of the cubicles.
 *
 * @author Brian Uri!
 */
public class Grid extends CharGrid {

	private static final char WALL = '#';
	private static final char OPEN = '.';
	private static final char START = 'O';

	private static final Pair<Integer> START_POSITION = new Pair<>(1, 1);

	private final StepStrategy<Pair<Integer>> STEP_STRATEGY = new StepStrategy<Pair<Integer>>() {
		@Override
		public List<Pair<Integer>> getNextSteps(Pair<Integer> current) {
			List<Pair<Integer>> nextSteps = current.getAdjacent();
			// Remove out of bounds or walls.
			nextSteps.removeIf(position -> position.getX() < 0 || position.getX() >= getWidth()
					|| position.getY() < 0 || position.getY() >= getHeight()
					|| get(position) == WALL);
			return (nextSteps);
		}
	};

	/**
	 * Constructor
	 */
	public Grid(int magicNumber) {
		super(new Pair<>(50, 50));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				int interim = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + magicNumber;
				int oneCount = Integer.toBinaryString(interim).replaceAll("0", "").length();
				char value = (oneCount % 2 == 0 ? OPEN : WALL);
				set(x, y, value);
			}
		}
		set(START_POSITION, START);
	}

	/**
	 * Returns the minimum number of steps to a single destination.
	 */
	public int getStepsTo(Pair<Integer> destination) {
		Map<Pair<Integer>, Pair<Integer>> cameFrom = Pathfinder.breadthFirstSearch(START_POSITION, STEP_STRATEGY);
		List<Pair<Integer>> destinations = new ArrayList<>();
		destinations.add(destination);
		List<Path<Pair<Integer>>> paths = Pathfinder.toPaths(destinations, cameFrom);
		// There will be 1 path returned, since only 1 destination was provided.
		return (paths.get(0).getLength());
	}

	/**
	 * Returns the number of locations reachable within X steps.
	 */
	public int getClosePositions(int steps) {
		Map<Pair<Integer>, Pair<Integer>> cameFrom = Pathfinder.breadthFirstSearch(START_POSITION, STEP_STRATEGY);
		// Add all potential destinations.
		List<Pair<Integer>> destinations = new ArrayList<>();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				char value = get(x, y);
				if (value == OPEN) {
					destinations.add(new Pair<>(x, y));
				}
			}
		}
		List<Path<Pair<Integer>>> paths = Pathfinder.toPaths(destinations, cameFrom);
		int count = 0;
		for (Path path : paths) {
			if (path.getLength() <= steps) {
				count++;
			}
		}

		// Add one for the path of staying on the start location without moving.
		return (count + 1);
	}
}