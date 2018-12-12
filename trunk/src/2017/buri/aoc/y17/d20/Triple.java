package buri.aoc.y17.d20;

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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",");
		buffer.append(getY()).append(",");
		buffer.append(getZ());
		return (buffer.toString());
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
