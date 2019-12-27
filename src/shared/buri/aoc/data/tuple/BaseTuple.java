package buri.aoc.data.tuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Base class for integer- and long-based tuples.
 * 
 * @author Brian Uri!
 */
public class BaseTuple<T extends Number> {
	private List<T> _values;
	
	/**
	 * Constructor
	 */
	protected BaseTuple() {
		_values = new ArrayList<>();
	}

	/**
	 * Returns the Manhattan distance to another tuple of the same size.
	 */
	public long getManhattanDistance(BaseTuple<T> tuple) {
		if (getValues().size() != tuple.getValues().size()) {
			throw new IllegalArgumentException("Cannot compute distance between unequally-sized tuples.");
		}
		long result = 0;
		for (int i = 0; i < getValues().size(); i++) {
			result += Math.abs(getValues().get(i).longValue() - tuple.getValues().get(i).longValue());
		}
		return (result);
	}

	/**
	 * Returns a comparison for the two values.
	 */
	protected int compare(T value1, T value2) {
		return (Long.compare(value1.longValue(), value2.longValue()));
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (T value : getValues()) {
			result += value.hashCode();
		}
		return (result);
	}

	@Override
	public boolean equals(Object obj) {
		BaseTuple tuple = (BaseTuple) obj;
		boolean equals = (this.getValues().size() == tuple.getValues().size());
		for (int i = 0; i < getValues().size(); i++) {
			equals = equals && (getValues().get(i).equals(tuple.getValues().get(i)));
		}
		return (equals);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<T> iterator = getValues().iterator(); iterator.hasNext();) {
			buffer.append(iterator.next());
			if (iterator.hasNext()) {
				buffer.append(",");
			}
		}
		return (buffer.toString());
	}

	/**
	 * Accessor for the raw values as a list.
	 */
	protected List<T> getValues() {
		return (_values);
	}
}
