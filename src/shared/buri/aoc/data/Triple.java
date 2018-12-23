package buri.aoc.data;

/**
 * Data class for a triple
 * 
 * @author Brian Uri!
 */
public class Triple {

	private long _x;
	private long _y;
	private long _z;

	/**
	 * Constructor
	 */
	public Triple(long x, long y, long z) {
		_x = x;
		_y = y;
		_z = z;
	}

	/**
	 * Adds a triple to this one.
	 */
	public void add(Triple triple) {
		setX(getX() + triple.getX());
		setY(getY() + triple.getY());
		setZ(getZ() + triple.getZ());
	}

	/**
	 * Returns the Manhattan distance to 0,0,0.
	 */
	public long getManhattanDistance() {
		return (Math.abs(getX()) + Math.abs(getY()) + Math.abs(getZ()));
	}

	/**
	 * Returns the Manhattan distance to another triple.
	 */
	public long getManhattanDistance(Triple triple) {
		return (Math.abs(getX() - triple.getX()) + Math.abs(getY() - triple.getY()) + Math.abs(getZ() - triple.getZ()));
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",");
		buffer.append(getY()).append(",");
		buffer.append(getZ());
		return (buffer.toString());
	}

	@Override
	public boolean equals(Object obj) {
		Triple t = (Triple) obj;
		return (getX() == t.getX() && getY() == t.getY() && getZ() == t.getZ());
	}

	@Override
	public int hashCode() {
		long result = getX();
		result = 7 * getY();
		result = 7 * getZ();
		return (Long.valueOf(result).intValue());
	}

	/**
	 * Accessor for the x
	 */
	public long getX() {
		return _x;
	}

	/**
	 * Accessor for the x
	 */
	public void setX(long x) {
		_x = x;
	}

	/**
	 * Accessor for the y
	 */
	public long getY() {
		return _y;
	}

	/**
	 * Accessor for the y
	 */
	public void setY(long y) {
		_y = y;
	}

	/**
	 * Accessor for the z
	 */
	public long getZ() {
		return _z;
	}

	/**
	 * Accessor for the z
	 */
	public void setZ(long z) {
		_z = z;
	}
}
