package buri.aoc.y21.d12;

import java.util.HashSet;
import java.util.Set;

/**
 * Data model for a cave.
 *
 * @author Brian Uri!
 */
public class Cave {
	private final String _name;
	private final Set<Cave> _neighbors;

	/**
	 * Constructor
	 */
	public Cave(String name) {
		_name = name;
		_neighbors = new HashSet<>();
	}

	/**
	 * Returns true if this is a small cave.
	 */
	public boolean isSmall() {
		return (getName().toLowerCase().equals(getName()));
	}

	/**
	 * Adds a neighbor to this cave. Ignores start (never go back to start).
	 */
	public void addNeighbor(Cave cave) {
		if (!cave.getName().equals("start")) {
			getNeighbors().add(cave);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (getName());
	}

	/**
	 * Accessor for the name.
	 */
	public String getName() {
		return (_name);
	}

	/**
	 * Accessor for the neighbors.
	 */
	public Set<Cave> getNeighbors() {
		return (_neighbors);
	}
}