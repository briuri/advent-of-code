package buri.aoc.y18.d20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import buri.aoc.data.Pair;

/**
 * A start-to-end ordered path between two positions, built from a breadth-first search.
 * 
 * @author Brian Uri!
 */
public class Path implements Comparable<Path> {
	private List<Pair> _path = new ArrayList<>();

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
	 * Returns the length of the path.
	 */
	public int getLength() {
		return (getPath().size());
	}

	/**
	 * Sort by length descending
	 */
	@Override
	public int compareTo(Path o) {
		return (o.getPath().size() - getPath().size());
	}

	/**
	 * Accessor for the raw list of path positions
	 */
	private List<Pair> getPath() {
		return _path;
	}
}
