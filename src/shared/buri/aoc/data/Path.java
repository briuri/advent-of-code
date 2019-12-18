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
	private List<Pair> _path = new ArrayList<>();

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
	public Path(Pair start, Pair end, Map<Pair, Pair> cameFrom) {
		Pair current = end;
		while (current != null) {
			getPath().add(current);
			current = cameFrom.get(current);
		}
		Collections.reverse(getPath());
	}

	/**
	 * Returns the start
	 */
	public Pair getStart() {
		return (getPath().get(0));
	}
	
	/**
	 * Returns the destination
	 */
	public Pair getEnd() {
		return (getPath().get(getLength() - 1));
	}
	
	/**
	 * Returns the next position on the path (assuming the unit is at the 1st one and needs to move). All paths have at
	 * least 2 nodes.
	 */
	public Pair getNextPosition() {
		return (getPath().get(1));
	}
	
	/**
	 * Returns the length of the path.
	 */
	public int getLength() {
		return (getPath().size());
	}
	
	/**
	 * Sort by length ascending first, then by reading order of the 2nd position on the path.
	 */
	@Override
	public int compareTo(Path o) {
		int compare = getPath().size() - o.getPath().size();
		if (compare == 0) {
			compare = getNextPosition().compareTo(o.getNextPosition());
		}
		return (compare);
	}

	/**
	 * Accessor for the raw list of path positions
	 */
	public List<Pair> getPath() {
		return _path;
	}
}
