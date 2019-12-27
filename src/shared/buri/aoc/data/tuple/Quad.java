package buri.aoc.data.tuple;

/**
 * Data class for a quad
 * 
 * @author Brian Uri!
 */
public class Quad<T extends Number> implements Comparable<Quad> {
	private T _x;
	private T _y;
	private T _z;
	private T _t;

	/**
	 * Constructor
	 */
	public Quad(T x, T y, T z, T t) {
		_x = x;
		_y = y;
		_z = z;
		_t = t;
	}

	/**
	 * Returns the Manhattan distance to another quad.
	 */
	public long getManhattanDistance(Quad quad) {
		long result = 0;
		result += Math.abs(getX().longValue() - quad.getX().longValue());
		result += Math.abs(getY().longValue() - quad.getY().longValue());
		result += Math.abs(getZ().longValue() - quad.getZ().longValue());
		result += Math.abs(getT().longValue() - quad.getT().longValue());
		return (result);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",");
		buffer.append(getY()).append(",");
		buffer.append(getZ()).append(",");
		buffer.append(getT());
		return (buffer.toString());
	}

	/**
	 * Sort order: by Y, then X, then Z, then T (top-left first)
	 */
	@Override
	public int compareTo(Quad o) {
		int compare = Long.valueOf(getY().longValue()).compareTo(Long.valueOf(o.getY().longValue()));
		if (compare == 0) {
			compare = Long.valueOf(getX().longValue()).compareTo(Long.valueOf(o.getX().longValue()));
		}
		if (compare == 0) {
			compare = Long.valueOf(getZ().longValue()).compareTo(Long.valueOf(o.getZ().longValue()));
		}
		if (compare == 0) {
			compare = Long.valueOf(getT().longValue()).compareTo(Long.valueOf(o.getT().longValue()));
		}
		return (compare);
	}
	
	@Override
	public boolean equals(Object obj) {
		Quad quad = (Quad) obj;
		return (getX().equals(quad.getX())
			&& getY().equals(quad.getY()) 
			&& getZ().equals(quad.getZ()) 
			&& getT().equals(quad.getT()));
	}

	@Override
	public int hashCode() {
		int result = getX().hashCode();
		result += getY().hashCode();
		result += getZ().hashCode();
		result += getT().hashCode();
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

	/**
	 * Accessor for the t
	 */
	public T getT() {
		return _t;
	}

	/**
	 * Accessor for the t
	 */
	public void setT(T t) {
		_t = t;
	}
}
