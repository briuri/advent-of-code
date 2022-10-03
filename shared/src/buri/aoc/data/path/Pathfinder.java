package buri.aoc.data.path;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import buri.aoc.data.tuple.BaseTuple;

/**
 * Utility class for search algorithms.
 * 
 * @author Brian Uri!
 */
public class Pathfinder {

	/**
	 * Do a BFS from one tuple to all other reachable tuples.
	 */
	public static <T extends BaseTuple> Map<T, T> breadthFirstSearch(T start, StepStrategy<T> strategy) {
		Queue<T> frontier = new ArrayDeque<>();
		frontier.add(start);
		Map<T, T> cameFrom = new HashMap<>();
		cameFrom.put(start, null);
		T current = null;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			for (T next : strategy.getNextSteps(current)) {
				if (!next.equals(start) && cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}
		return (cameFrom);
	}
	
	/**
	 * Constructs all paths from the start to the destinations and returns them with the shortest paths first.
	 */
	public static <T extends BaseTuple> List<Path> toPaths(T start, List<T> destinations, Map<T, T> cameFrom) {
		List<Path> paths = new ArrayList<>();
		for (T destination : destinations) {
			if (cameFrom.get(destination) != null) {
				paths.add(new Path(start, destination, cameFrom));
			}
		}
		Collections.sort(paths);
		return (paths);
	}
}
