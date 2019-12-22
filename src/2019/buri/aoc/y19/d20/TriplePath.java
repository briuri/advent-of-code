package buri.aoc.y19.d20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import buri.aoc.data.Triple;

/**
 * A start-to-end ordered path between two 3D positions, built from a breadth-first search.
 * 
 * Like a slimmed down 2D Path (buri.aoc.data) that uses Triples instead of Pairs.
 * 
 * @author Brian Uri!
 */
public class TriplePath implements Comparable<TriplePath> {
	private List<Triple> _positions = new ArrayList<>();

	/**
	 * Factory method to convert the results of a BFS into paths.
	 */
	public static List<TriplePath> buildPaths(Triple start, List<Triple> destinations, Map<Triple, Triple> cameFrom) {
		List<TriplePath> shortestPaths = new ArrayList<>();
		for (Triple destination : destinations) {
			if (cameFrom.get(destination) != null) {
				shortestPaths.add(new TriplePath(start, destination, cameFrom));
			}
		}
		Collections.sort(shortestPaths);
		return (shortestPaths);
	}
	
	/**
	 * Constructor
	 */
	private TriplePath(Triple start, Triple end, Map<Triple, Triple> cameFrom) {
		Triple current = end;
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
	 * Sort by length ascending.
	 */
	@Override
	public int compareTo(TriplePath o) {
		int compare = getPositions().size() - o.getPositions().size();
		return (compare);
	}

	/**
	 * Accessor for the raw list of path positions
	 */
	public List<Triple> getPositions() {
		return _positions;
	}
}
