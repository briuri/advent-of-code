package buri.aoc.data.tuple;

/**
 * Data class for a triple
 * 
 * @author Brian Uri!
 */
public class Triple<T extends Number> implements Comparable<Triple> {
	private T _x;
	private T _y;
	private T _z;

	/**
	 * Constructor
	 */
	public Triple(T x, T y, T z) {
		_x = x;
		_y = y;
		_z = z;
	}

	/**
	 * Returns a copy of this triple.
	 */
	public Triple copy() {
		return (new Triple(getX(), getY(), getZ()));
	}

	/**
	 * Returns the Manhattan distance to another triple.
	 */
	public long getManhattanDistance(Triple triple) {
		long result = 0;
		result += Math.abs(getX().longValue() - triple.getX().longValue());
		result += Math.abs(getY().longValue() - triple.getY().longValue());
		result += Math.abs(getZ().longValue() - triple.getZ().longValue());
		return (result);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",");
		buffer.append(getY()).append(",");
		buffer.append(getZ());
		return (buffer.toString());
	}

	/**
	 * Sort order: by Y, then X, then Z (top-left first)
	 */
	@Override
	public int compareTo(Triple o) {
		int compare = Long.valueOf(getY().longValue()).compareTo(Long.valueOf(o.getY().longValue()));
		if (compare == 0) {
			compare = Long.valueOf(getX().longValue()).compareTo(Long.valueOf(o.getX().longValue()));
		}
		if (compare == 0) {
			compare = Long.valueOf(getZ().longValue()).compareTo(Long.valueOf(o.getZ().longValue()));
		}
		return (compare);
	}

	@Override
	public boolean equals(Object obj) {
		Triple triple = (Triple) obj;
		return (getX().equals(triple.getX())
			&& getY().equals(triple.getY())
			&& getZ().equals(triple.getZ()));
	}

	@Override
	public int hashCode() {
		int result = getX().hashCode();
		result += getY().hashCode();
		result += getZ().hashCode();
		return (result);
	}

	/**
	 * Accessor for the x
	 */
	public T getX() {
		return _x;
	}

	/**
	 * Accessor for the x
	 */
	public void setX(T x) {
		_x = x;
	}

	/**
	 * Accessor for the y
	 */
	public T getY() {
		return _y;
	}

	/**
	 * Accessor for the y
	 */
	public void setY(T y) {
		_y = y;
	}

	/**
	 * Accessor for the z
	 */
	public T getZ() {
		return _z;
	}

	/**
	 * Accessor for the z
	 */
	public void setZ(T z) {
		_z = z;
	}
}
