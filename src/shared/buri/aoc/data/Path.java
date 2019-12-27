package buri.aoc.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A start-to-end ordered path between two positions, built from a breadth-first search.
 * 
 * @author Brian Uri!
 */
public class Path<T extends Comparable> implements Comparable<Path<T>> {
	private List<T> _positions = new ArrayList<>();

	/**
	 * Factory method to convert the results of a BFS into paths.
	 */
	public static <T extends Comparable> List<Path> buildPaths(T start, List<T> destinations, Map<T, T> cameFrom) {
		List<Path> shortestPaths = new ArrayList<>();
		for (T destination : destinations) {
			if (cameFrom.get(destination) != null) {
				shortestPaths.add(new Path(start, destination, cameFrom));
			}
		}
		Collections.sort(shortestPaths);
		return (shortestPaths);
	}
	
	/**
	 * Constructor
	 */
	private Path(T start, T destination, Map<T, T> cameFrom) {
		T current = destination;
		while (current != null) {
			getPositions().add(current);
			current = cameFrom.get(current);
		}
		Collections.reverse(getPositions());
	}
		
	/**
	 * Returns the length of the path.
	 */
	public int getLength() {
		return (getPositions().size() - 1);
	}
	
	/**
	 * Sort by length ascending first, then by reading order of the 2nd position on the path.
	 */
	@Override
	public int compareTo(Path<T> o) {
		int compare = getPositions().size() - o.getPositions().size();
		if (compare == 0) {
			compare = getPositions().get(1).compareTo(o.getPositions().get(1));
		}
		return (compare);
	}

	/**
	 * Accessor for the raw list of path positions
	 */
	public List<T> getPositions() {
		return _positions;
	}
}
