package buri.aoc.y18.d15;

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
	private List<Position> _path = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Path(Position start, Position end, Map<Position, Position> cameFrom) {
		Position current = end;
		while (current != null) {
			getPath().add(current);
			current = cameFrom.get(current);
		}
		Collections.reverse(getPath());
	}

	/**
	 * Returns the next position on the path (assuming the unit is at the 1st one and needs to move). All paths have at
	 * least 2 nodes.
	 */
	public Position getNextPosition() {
		return (getPath().get(1));
	}

	/**
	 * Sort by length first, then by reading order of the 2nd position on the path.
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
	private List<Position> getPath() {
		return _path;
	}
}
