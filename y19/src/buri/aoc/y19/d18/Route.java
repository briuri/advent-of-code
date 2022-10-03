package buri.aoc.y19.d18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data model for a route between two points.
 * 
 * @author Brian Uri!
 */
public class Route {
	private Set<Character> _endpoints;
	private int _steps;
	private Set<Character> _doors;

	/**
	 * Constructor
	 */
	public Route(Character start, Character end, int steps) {
		_endpoints = new HashSet<>();
		getEndpoints().add(start);
		getEndpoints().add(end);
		_steps = steps;
		_doors = new HashSet<>();
	}

	/**
	 * Returns true if every door on this route can be unlocked with the given keys.
	 */
	public boolean isTraversable(List<Character> keys) {
		boolean isTraversable = true;
		for (Character door : getDoors()) {
			isTraversable = isTraversable && keys.contains(Character.toLowerCase(door));
		}
		return (isTraversable);
	}

	@Override
	public String toString() {
		return ("endpoints=" + getEndpoints() + " doors=" + getDoors() + " steps=" + getSteps());
	}

	/**
	 * Returns the opposite endpoint.
	 */
	public Character getOtherEndpoint(Character character) {
		List<Character> list = new ArrayList<>(getEndpoints());
		int index = list.get(1).equals(character) ? 0 : 1;
		return (list.get(index));
	}

	/**
	 * Accessor for the endpoints
	 */
	public Set<Character> getEndpoints() {
		return _endpoints;
	}

	/**
	 * Accessor for the steps
	 */
	public int getSteps() {
		return _steps;
	}

	/**
	 * Accessor for the doors
	 */
	public Set<Character> getDoors() {
		return _doors;
	}
}
