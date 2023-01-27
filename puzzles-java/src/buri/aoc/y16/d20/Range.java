package buri.aoc.y16.d20;

/**
 * Base class for an X-to-Y range (inclusive).
 *
 * @author Brian Uri!
 */
public class Range implements Comparable<Range> {
	private Long _min;
	private Long _max;

	/**
	 * String-based Constructor with format "x-y"
	 */
	public Range(String data) {
		String[] tokens = data.split("-");
		_min = Long.valueOf(tokens[0]);
		_max = Long.valueOf(tokens[1]);
	}

	/**
	 * Merges a range into this one. Returns true if the ranges overlap, false if they do not.
	 */
	public boolean merge(Range range) {
		if (getMax() + 1 < range.getMin() || range.getMax() + 1 < getMin()) {
			return (false);
		}
		setMin(Math.min(getMin(), range.getMin()));
		setMax(Math.max(getMax(), range.getMax()));
		return (true);
	}

	/**
	 * Sort by min, then max, ascending order.
	 */
	@Override
	public int compareTo(Range o) {
		int compare = getMin().compareTo(o.getMin());
		if (compare == 0) {
			compare = getMax().compareTo(o.getMax());
		}
		return (compare);
	}

	@Override
	public boolean equals(Object obj) {
		Range p = (Range) obj;
		return (getMin() == p.getMin() && getMax() == p.getMax());
	}

	@Override
	public int hashCode() {
		int result = Long.hashCode(getMin());
		result += 7 * Long.hashCode(getMax());
		return (result);
	}

	@Override
	public String toString() {
		return (getMin() + "-" + getMax());
	}

	/**
	 * Accessor for min
	 */
	public Long getMin() {
		return _min;
	}

	/**
	 * Accessor for min
	 */
	private void setMin(Long min) {
		_min = min;
	}

	/**
	 * Accessor for max
	 */
	public Long getMax() {
		return _max;
	}

	/**
	 * Accessor for max
	 */
	private void setMax(Long max) {
		_max = max;
	}
}