package buri.aoc.y18.d18;

import java.util.HashMap;
import java.util.Map;

/**
 * The total counts of open, trees, and yards in 8 surrounding cells.
 *
 * @author Brian Uri!
 */
public class AdjacentValues {

	private final Map<Character, Integer> _values;

	/**
	 * Constructor
	 */
	public AdjacentValues() {
		_values = new HashMap<>();
		getValues().put(Acreage.OPEN, 0);
		getValues().put(Acreage.TREES, 0);
		getValues().put(Acreage.YARD, 0);
	}

	/**
	 * Returns the count for some type of tile.
	 */
	public int get(char value) {
		return (getValues().get(value));
	}

	/**
	 * Adds 1 to the count of some type of tile.
	 */
	public void add(char value) {
		getValues().put(value, getValues().get(value) + 1);
	}

	/**
	 * Accessor for the values
	 */
	private Map<Character, Integer> getValues() {
		return _values;
	}
}