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
public class Path implements Comparable<Path> {
	private List<Pair> _positions = new ArrayList<>();

	/**
	 * Factory method to convert the results of a BFS into paths.
	 */
	public static List<Path> buildPaths(Pair start, List<Pair> destinations, Map<Pair, Pair> cameFrom) {
		List<Path> shortestPaths = new ArrayList<>();
		for (Pair destination : destinations) {
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
	private Path(Pair start, Pair end, Map<Pair, Pair> cameFrom) {
		Pair current = end;
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
	public int compareTo(Path o) {
		int compare = getPositions().size() - o.getPositions().size();
		if (compare == 0) {
			compare = getPositions().get(1).compareTo(o.getPositions().get(1));
		}
		return (compare);
	}

	/**
	 * Accessor for the raw list of path positions
	 */
	public List<Pair> getPositions() {
		return _positions;
	}
}
