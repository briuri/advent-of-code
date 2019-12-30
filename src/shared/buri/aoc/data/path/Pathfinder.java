package buri.aoc.data.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import buri.aoc.data.tuple.BaseTuple;

/**
 * Utility class for search algorithms.
 * 
 * @author Brian Uri!
 */
public class Pathfinder {

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
