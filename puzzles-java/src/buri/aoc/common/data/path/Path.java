package buri.aoc.common.data.path;

import buri.aoc.common.data.tuple.BaseTuple;
import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.data.tuple.Quad;
import buri.aoc.common.data.tuple.Triple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A start-to-end ordered path between two tuples, built from a breadth-first search.
 *
 * @author Brian Uri!
 */
public class Path<T extends BaseTuple> implements Comparable<Path<T>> {
	private final List<T> _steps = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Path(T destination, Map<T, T> cameFrom) {
		T current = destination;
		while (current != null) {
			getSteps().add(current);
			current = cameFrom.get(current);
		}
		Collections.reverse(getSteps());
	}

	/**
	 * Returns the length of the path.
	 */
	public int getLength() {
		return (getSteps().size() - 1);
	}

	/**
	 * Sort by length ascending first, then by reading order of the 2nd position on the path.
	 */
	@Override
	public int compareTo(Path<T> o) {
		int compare = getSteps().size() - o.getSteps().size();
		if (compare == 0) {
			if (getSteps().get(1) instanceof Pair) {
				compare = ((Pair) getSteps().get(1)).compareTo((Pair) o.getSteps().get(1));
			}
			else if (getSteps().get(1) instanceof Triple) {
				compare = ((Triple) getSteps().get(1)).compareTo((Triple) o.getSteps().get(1));
			}
			else if (getSteps().get(1) instanceof Quad) {
				compare = ((Quad) getSteps().get(1)).compareTo((Quad) o.getSteps().get(1));
			}
		}
		return (compare);
	}

	/**
	 * Accessor for the raw list of path steps
	 */
	public List<T> getSteps() {
		return _steps;
	}
}